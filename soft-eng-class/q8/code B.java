@J2ktIncompatible
@GwtIncompatible // TODO
@SuppressWarnings("ShortCircuitBoolean")
public static long checkedPow(long b, int k) {
  checkNonNegative(String.valueOf("exponent"), k);
  if (b >= -2 & b <= 2) {
    switch ((int) b) {
      case 0:
        return (k == 0) ? 1 : 0;
      case 1:
        return 1;
      case (-1):
        return ((k & 1) == 0) ? 1 : -1;
      case 2:
        checkNoOverflow(k < Long.SIZE - 1, "checkedPow", b, k);
        return 1L << k;
      case (-2):
        checkNoOverflow(k < Long.SIZE, "checkedPow", b, k);
        return ((k & 1) == 0) ? (1L << k) : (-1L << k);
      default:
        throw new AssertionError();
    }
  }
  long accum = 1;
  while (true) {
    switch (k) {
      case 0:
        return accum;
      case 1:
        return checkedMultiply(accum, b);
      default:
        if ((k & 1) != 0) {
          accum = checkedMultiply(accum, b);
        }
        k >>= 1;
        if (k > 0) {
          checkNoOverflow(
              -FLOOR_SQRT_MAX_LONG <= b && b <= FLOOR_SQRT_MAX_LONG, "checkedPow", b, k);
          b *= b;
        }
    }
  }
}