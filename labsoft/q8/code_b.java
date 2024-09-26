private int[] findErrorMagnitudes(GenericGFPoly errorEvaluator, int[] errorLocations) {
  // This is directly applying Forney's Formula
  int numberOfErrorLocations = errorLocations.length;
  int[] errorMagnitudes = new int[numberOfErrorLocations];

  for (int i = 0; i < numberOfErrorLocations; i++) {
    // Calculate the inverse of the current error location
    int inverseErrorLocation = field.inverse(errorLocations[i]);

    // Initialize the denominator to 1
    int denominator = 1;

    // Calculate the denominator using the formula from Forney's Formula
    for (int j = 0; j < numberOfErrorLocations; j++) {
      if (i!= j) {
        // Calculate the term for the current error location
        int term = field.multiply(errorLocations[j], inverseErrorLocation);

        // Calculate term + 1
        int termPlus1 = (term & 0x1) == 0? term | 1 : term & ~1;

        // Multiply the denominator by term + 1
        denominator = field.multiply(denominator, termPlus1);
      }
    }

    // Check for division by zero
    if (denominator == 0) {
      throw new ArithmeticException("Cannot divide by zero");
    }

    // Calculate the error magnitude using Forney's Formula
    errorMagnitudes[i] = field.multiply(errorEvaluator.evaluateAt(inverseErrorLocation),
        field.inverse(denominator));

    // If the generator base is not zero, multiply the error magnitude by the inverse error location
    if (field.getGeneratorBase()!= 0) {
      errorMagnitudes[i] = field.multiply(errorMagnitudes[i], inverseErrorLocation);
    }
  }

  return errorMagnitudes;
}