@J2ktIncompatible
@GwtIncompatible // TODO
public static long pow(long b, int k) {
  checkNonNegative("exponent", k);
  if (-2 <= b && b <= 2) {
    switch ((int) b) {
      case 0:
        return (k == 0) ? 1 : 0;
      case 1:
        return 1;
      case (-1):
        return ((k & 1) == 0) ? 1 : -1;
      case 2:
        return (k < Long.SIZE) ? 1L << k : 0;
      case (-2):
        if (k < Long.SIZE) {
          return ((k & 1) == 0) ? 1L << k : -(1L << k);
        } else {
          return 0;
        }
      default:
        throw new AssertionError();
    }
  }
  final int zero = 0;
  final int one = 1;
  final int negativeOne = -1;
  final int two = 2;
  final int negativeTwo = -2;
  for (long accum = 1; ; k >>= 1) {
    switch (k) {
      case zero:
        return accum;
      case one:
        return accum * b;
      default:
        accum *= ((k & 1) == 0) ? one : b;
        b *= b;
    }
  }
}