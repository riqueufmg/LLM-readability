private static final String INVALID_GUARDS_ERROR = "Invalid start/end guards: ";

@Override
public boolean[] encode(String contents) {

  if (contents.length() < 2) {
    // Can't have a start/end guard, so tentatively add default guards
    contents = DEFAULT_GUARD + contents + DEFAULT_GUARD;
  } else {
    // Verify input and calculate decoded length.
    char firstChar = Character.toUpperCase(contents.charAt(0));
    char lastChar = Character.toUpperCase(contents.charAt(contents.length() - 1));
    boolean startsNormal = CodaBarReader.arrayContains(START_END_CHARS, firstChar);
    boolean endsNormal = CodaBarReader.arrayContains(START_END_CHARS, lastChar);
    boolean startsAlt = CodaBarReader.arrayContains(ALT_START_END_CHARS, firstChar);
    boolean endsAlt = CodaBarReader.arrayContains(ALT_START_END_CHARS, lastChar);
    if (startsNormal) {
      if (!endsNormal) {
        throw new IllegalArgumentException(INVALID_GUARDS_ERROR + contents);
      }
      // else already has valid start/end
    } else if (startsAlt) {
      if (!endsAlt) {
        throw new IllegalArgumentException(INVALID_GUARDS_ERROR + contents);
      }
      // else already has valid start/end
    } else {
      // Doesn't start with a guard
      if (endsNormal || endsAlt) {
        throw new IllegalArgumentException(INVALID_GUARDS_ERROR + contents);
      }
      // else doesn't end with guard either, so add a default
      contents = DEFAULT_GUARD + contents + DEFAULT_GUARD;
    }
  }

  // The start character and the end character are decoded to 10 length each.
  int resultLength = 20;
  for (int i = 1; i < contents.length() - 1; i++) {
    if (Character.isDigit(contents.charAt(i)) || contents.charAt(i) == '-' || contents.charAt(i) == '$') {
      resultLength += 9;
    } else if (CodaBarReader.arrayContains(CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED, contents.charAt(i))) {
      resultLength += 10;
    } else {
      throw new IllegalArgumentException("Cannot encode : '" + contents.charAt(i) + '\'');
    }
  }
  // A blank is placed between each character.
  resultLength += contents.length() - 1;

  boolean[] result = new boolean[resultLength];
  int position = 0;
  for (int index = 0; index < contents.length(); index++) {
    char c = Character.toUpperCase(contents.charAt(index));
    if (index == 0 || index == contents.length() - 1) {
      // The start/end chars are not in the CodaBarReader.ALPHABET.
      switch (c) {
        case 'T':
          c = 'A';
          break;
        case 'N':
          c = 'B';
          break;
        case '*':
          c = 'C';
          break;
        case 'E':
          c = 'D';
          break;
      }
    }
    int code = 0;
    for (int i = 0; i < CodaBarReader.ALPHABET.length; i++) {
      // Found any, because I checked above.
      if (c == CodaBarReader.ALPHABET[i]) {
        code = CodaBarReader.CHARACTER_ENCODINGS[i];
        break;
      }
    }
    boolean color = true;
    int counter = 0;
    int bit = 0;
    while (bit < 7) { // A character consists of 7 digit.
      result[position] = color;
      position++;
      if (((code >> (6 - bit)) & 1) == 0 || counter == 1) {
        color = !color; // Flip the color.
        bit++;
        counter = 0;
      } else {
        counter++;
      }
    }
    if (index < contents.length() - 1) {
      result[position] = false;
      position++;
    }
  }
  return result;
}