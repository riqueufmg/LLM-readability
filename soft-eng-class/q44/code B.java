private static final String CLASS_DUMMY = "class Dummy {\n";
private static final String BRACE = "{\n";

private SnippetWrapper snippetWrapper(SnippetKind kind, String source, int initialIndent) {
  /*
   * Synthesize a dummy class around the code snippet provided by Eclipse.  The dummy class is
   * correctly formatted -- the blocks use correct indentation, etc.
   */
  switch (kind) {
    case COMPILATION_UNIT:
    case CLASS_BODY_DECLARATIONS:
      {
        SnippetWrapper wrapper = new SnippetWrapper();
        for (int i = 1; i <= initialIndent; i++) {
          wrapper.append(CLASS_DUMMY).append(createIndentationString(i));
        }
        wrapper.appendSource(source);
        wrapper.closeBraces(initialIndent);
        return wrapper;
      }
    case STATEMENTS:
    case EXPRESSION:
      {
        SnippetWrapper wrapper = new SnippetWrapper();
        wrapper.append(CLASS_DUMMY).append(createIndentationString(1));
        for (int i = 2; i <= initialIndent; i++) {
          wrapper.append(BRACE).append(createIndentationString(i));
        }
        if (kind == SnippetKind.EXPRESSION) {
          wrapper.append("Object o = ");
        }
        wrapper.appendSource(source);
        if (kind == SnippetKind.EXPRESSION) {
          wrapper.append(";");
        }
        wrapper.closeBraces(initialIndent);
        return wrapper;
      }
    default:
      throw new IllegalArgumentException("Unknown snippet kind: " + kind);
  }
}