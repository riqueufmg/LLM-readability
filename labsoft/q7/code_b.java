private void waitForInterrupt(Thread currentThread) {
    boolean restoreInterruptedBit = false;
    int spinCount = 0;
    Runnable state = get();
    Blocker blocker = null;
    while (state instanceof Blocker || state == PARKED) {
        if (state instanceof Blocker) {
            blocker = (Blocker) state;
        }
        spinCount++;
        if (spinCount > MAX_BUSY_WAIT_SPINS) {
            if (state == PARKED || compareAndSet(state, PARKED)) {
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
    // TODO: Clear interrupt status here? We currently don't, which means that an interrupt
    // before, during, or after runInterruptibly() (unless it produced an InterruptedException
    // caught above) can linger and affect listeners.
}