@Override
public boolean cancel(boolean interruptIfRunning) {
  InCompletionOrderState<T> localState = state;
  if (super.cancel(interruptIfRunning)) {
    /*
     * requireNonNull is generally safe: If cancel succeeded, then this Future was still
     * pending, so its `state` field hasn't been nulled out yet.
     *
     * OK, it's technically possible for this to fail in the presence of unsafe publishing, as
     * discussed in the comments in TimeoutFuture. TODO(cpovirk): Maybe check for null before
     * calling recordOutputCancellation?
     */
    requireNonNull(localState).recordOutputCancellation(interruptIfRunning);
    return true;
  }
  // TODO: Add additional handling if cancellation fails
  return false;
}