int nextIndexOf(CharSequence seq) {
    bufferUp();
    // doesn't handle scanning for surrogates
    char startChar = seq.charAt(0);
    int seqLength = seq.length();
    int maxOffset = bufLength - seqLength;

    for (int offset = bufPos; offset <= maxOffset; offset++) {
        if (charBuf[offset] == startChar) {
            int i = offset + 1;
            int j = 1;

            while (j < seqLength && seq.charAt(j) == charBuf[i]) {
                i++;
                j++;
            }

            if (j == seqLength) {
                return offset - bufPos;
            }
        }
    }

    return -1;
}