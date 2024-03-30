@J2ktIncompatible
@GwtIncompatible // failing tests
public static int pow(int b, int k) {
  checkNonNegative("exponent", k);
  final int ZERO = 0;
  final int ONE = 1;
  final int MINUS_ONE = -1;
  final int TWO = 2;
  final int MINUS_TWO = -2;
  switch (b) {
    case ZERO:
      return (k == ZERO) ? ONE : ZERO;
    case ONE:
      return ONE;
    case MINUS_ONE:
      return ((k & 1) == ZERO) ? ONE : MINUS_ONE;
    case TWO:
      return (k < Integer.SIZE) ? (ONE << k) : ZERO;
    case MINUS_TWO:
      if (k < Integer.SIZE) {
        return ((k & 1) == ZERO) ? (ONE << k) : -(ONE << k);
      } else {
        return ZERO;
      }
    default:
      // continue below to handle the general case
  }
  for (int accum = ONE; ; k >>= 1) {
    switch (k) {
      case ZERO:
        return accum;
      case ONE:
        return b * accum;
      default:
        accum *= ((k & 1) == ZERO) ? ONE : b;
        b *= b;
    }
  }
}
