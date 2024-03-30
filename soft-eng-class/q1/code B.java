@Override
  public void uncaughtException(Thread t, Throwable e) {
    try {
      logger
          .get()
          .log(
              SEVERE,
              String.format(Locale.ROOT, "Caught an exception in %s.  Shutting down.", t),
              e);
    } catch (Throwable errorInLogging) { // sneaky checked exception
      // If logging fails, e.g. due to missing memory, at least try to log the
      // message and the cause for the failed logging.
      logger.get().log(SEVERE, e.getMessage());
      logger.get().log(SEVERE, errorInLogging.getMessage());
    } finally {
      runtime.exit(1);
    }
  }
}