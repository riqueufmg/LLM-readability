private static int applyMaskPenaltyRule1Internal(ByteMatrix matrix, boolean isHorizontal) {
  int penalty = 0;
  int iLimit = isHorizontal ? matrix.getHeight() : matrix.getWidth();
  int jLimit = isHorizontal ? matrix.getWidth() : matrix.getHeight();
  byte[][] array = matrix.getArray();
  for (int i = 0; i < iLimit; i++) {
    penalty += calculatePenaltyForSameBitCells(array, i, jLimit, isHorizontal);
  }
  return penalty;
}

private static int calculatePenaltyForSameBitCells(byte[][] array, int i, int jLimit, boolean isHorizontal) {
  int penalty = 0;
  int numSameBitCells = 0;
  int prevBit = -1;
  for (int j = 0; j < jLimit; j++) {
    int bit = isHorizontal ? array[i][j] : array[j][i];
    if (bit == prevBit) {
      numSameBitCells++;
    } else {
      if (numSameBitCells >= 5) {
        penalty += N1 + (numSameBitCells - 5);
      }
      numSameBitCells = 1;  // Include the cell itself.
      prevBit = bit;
    }
  }
  if (numSameBitCells >= 5) {
    penalty += N1 + (numSameBitCells - 5);
  }
  return penalty;
}