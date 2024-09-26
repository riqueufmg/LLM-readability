private void waitForInterrupt(Thread currentThread) {
    /*
     * If someone called cancel(true), it is possible that the interrupted bit hasn't been set yet.
     * Wait for the interrupting thread to set DONE. (See interruptTask().) We want to wait so that
     * the interrupting thread doesn't interrupt the _next_ thing to run on this thread.
     *
     * Note: We don't reset the interrupted bit, just wait for it to be set. If this is a thread
     * pool thread, the thread pool will reset it for us. Otherwise, the interrupted bit may have
     * been intended for something else, so don't clear it.
     */
    boolean restoreInterruptedBit = false;
    int spinCount = 0;
    // Interrupting Cow Says:
    //  ______
    // < Spin >
    //  ------
    //        \   ^__^
    //         \  (oo)\_______
    //            (__)\       )\/\
    //                ||----w |
    //                ||     ||
    Runnable state = get();
    Blocker blocker = null;
    while (state instanceof Blocker || state == PARKED) {
      if (state instanceof Blocker) {
        blocker = (Blocker) state;
      }
      spinCount++;
      if (spinCount > MAX_BUSY_WAIT_SPINS) {
        /*
         * If we have spun a lot, just park ourselves. This will save CPU while we wait for a slow
         * interrupting thread. In theory, interruptTask() should be very fast, but due to
         * InterruptibleChannel and JavaLangAccess.blockedOn(Thread, Interruptible), it isn't
         * predictable what work might be done. (e.g., close a file and flush buffers to disk). To
         * protect ourselves from this, we park ourselves and tell our interrupter that we did so.
         */
        if (state == PARKED || compareAndSet(state, PARKED)) {
          // Interrupting Cow Says:
          //  ______
          // < Park >
          //  ------
          //        \   ^__^
          //         \  (oo)\_______
          //            (__)\       )\/\
          //                ||----w |
          //                ||     ||
          // We need to clear the interrupted bit prior to calling park and maintain it in case we
          // wake up spuriously.
          restoreInterruptedBit = Thread.interrupted() || restoreInterruptedBit;
          LockSupport.park(blocker);
        }
      } else {
        Thread.yield();
      }
      state = get();
    }
    if (restoreInterruptedBit) {
      currentThread.interrupt();
    }
    /*
     * TODO(cpovirk): Clear interrupt status here? We currently don't, which means that an interrupt
     * before, during, or after runInterruptibly() (unless it produced an InterruptedException
     * caught above) can linger and affect listeners.
     */
  }