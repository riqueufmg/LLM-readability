void format() {
  boolean appendModuleSource = !moduleStack.isEmpty();
  String preposition = preposition("at ");
  String locatingPreposition = preposition("while locating ");
  String threadPreposition = preposition("in thread ");

  if (source instanceof Dependency) {
    formatDependency((Dependency<?>) source);
  } else if (source instanceof InjectionPoint) {
    formatInjectionPoint(null, (InjectionPoint) source);
  } else if (source instanceof Class) {
    formatter.format("%s%s\n", preposition, StackTraceElements.forType((Class<?>) source));
  } else if (source instanceof Member) {
    formatMember((Member) source);
  } else if (source instanceof TypeLiteral) {
    formatter.format("%s%s\n", locatingPreposition, source);
  } else if (source instanceof Key) {
    formatKey((Key<?>) source);
  } else if (source instanceof Thread) {
    appendModuleSource = false;
    formatter.format("%s%s\n", threadPreposition, source);
  } else {
    formatter.format("%s%s\n", preposition, source);
  }

  if (appendModuleSource) {
    formatter.format("%s \\_ installed by: %s\n", INDENT, moduleStack);
  }
}