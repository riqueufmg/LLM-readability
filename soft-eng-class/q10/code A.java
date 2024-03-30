public void add(double x, double y) {
    // We extend the recursive expression for the one-variable case at Art of Computer Programming
    // vol. 2, Knuth, 4.2.2, (16) to the two-variable case. We have two value series x_i and y_i.
    // We define the arithmetic means X_n = 1/n \sum_{i=1}^n x_i, and Y_n = 1/n \sum_{i=1}^n y_i.
    // We also define the sum of the products of the differences from the means
    //           C_n = \sum_{i=1}^n x_i y_i - n X_n Y_n
    // for all n >= 1. Then for all n > 1:
    //       C_{n-1} = \sum_{i=1}^{n-1} x_i y_i - (n-1) X_{n-1} Y_{n-1}
    // C_n - C_{n-1} = x_n y_n - n X_n Y_n + (n-1) X_{n-1} Y_{n-1}
    //               = x_n y_n - X_n [ y_n + (n-1) Y_{n-1} ] + [ n X_n - x_n ] Y_{n-1}
    //               = x_n y_n - X_n y_n - x_n Y_{n-1} + X_n Y_{n-1}
    //               = (x_n - X_n) (y_n - Y_{n-1})
    xStats.add(x);
    if (isFinite(x) && isFinite(y)) {
      if (xStats.count() > 1) {
        sumOfProductsOfDeltas += (x - xStats.mean()) * (y - yStats.mean());
      }
    } else {
      sumOfProductsOfDeltas = NaN;
    }
    yStats.add(y);
}