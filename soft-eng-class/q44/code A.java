private SnippetWrapper snippetWrapper(SnippetKind kind, String source, int initialIndent) {
  /*
   * Synthesize a dummy class around the code snippet provided by Eclipse.  The dummy class is
   * correctly formatted -- the blocks use correct indentation, etc.
   */
  switch (kind) {
    case COMPILATION_UNIT:
      {
        SnippetWrapper wrapper = new SnippetWrapper();
        for (int i = 1; i <= initialIndent; i++) {
          wrapper.append("class Dummy {\n").append(createIndentationString(i));
        }
        wrapper.appendSource(source);
        wrapper.closeBraces(initialIndent);
        return wrapper;
      }
    case CLASS_BODY_DECLARATIONS:
      {
        SnippetWrapper wrapper = new SnippetWrapper();
        for (int i = 1; i <= initialIndent; i++) {
          wrapper.append("class Dummy {\n").append(createIndentationString(i));
        }
        wrapper.appendSource(source);
        wrapper.closeBraces(initialIndent);
        return wrapper;
      }
    case STATEMENTS:
      {
        SnippetWrapper wrapper = new SnippetWrapper();
        wrapper.append("class Dummy {\n").append(createIndentationString(1));
        for (int i = 2; i <= initialIndent; i++) {
          wrapper.append("{\n").append(createIndentationString(i));
        }
        wrapper.appendSource(source);
        wrapper.closeBraces(initialIndent);
        return wrapper;
      }
    case EXPRESSION:
      {
        SnippetWrapper wrapper = new SnippetWrapper();
        wrapper.append("class Dummy {\n").append(createIndentationString(1));
        for (int i = 2; i <= initialIndent; i++) {
          wrapper.append("{\n").append(createIndentationString(i));
        }
        wrapper.append("Object o = ");
        wrapper.appendSource(source);
        wrapper.append(";");
        wrapper.closeBraces(initialIndent);
        return wrapper;
      }
    default:
      throw new IllegalArgumentException("Unknown snippet kind: " + kind);
  }
}