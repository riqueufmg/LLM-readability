public void add(double x, double y) {
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