@Override
protected void formatDetail(List<ErrorDetail<?>> others, Formatter formatter) {
  formatter.format("\n%s\n", Messages.bold("Duplicates:"));
  int duplicateIndex = 1;
  for (Map.Entry<T, Collection<Element<T>>> entry : elements.asMap().entrySet()) {
    formatter.format("%-2s: ", duplicateIndex++);
    if (entry.getValue().size() > 1) {
      Set<String> valuesAsString =
          entry.getValue().stream()
              .map(element -> element.value.toString())
              .collect(Collectors.toSet());
      if (valuesAsString.size() == 1) {
        // String representation of the duplicates elements are the same, so only print out one.
        formatter.format("Element: %s\n", Messages.redBold(valuesAsString.iterator().next()));
        formatter.format("    Bound at:\n");
        int index = 1;
        for (Element<T> element : entry.getValue()) {
          formatter.format("    %-2s: ", index++);
          formatElement(element, formatter);
        }
      } else {
        // Print out all elements as string when there are different string representations of the
        // elements. To keep the logic simple, same strings are not grouped together unless all
        // elements have the same string represnetation. This means some strings may be printed
        // out multiple times.
        // There is no indentation for the first duplicate element.
        boolean indent = false;
        for (Element<T> element : entry.getValue()) {
          if (indent) {
            formatter.format("    ");
          } else {
            indent = true;
          }
          formatter.format("Element: %s\n", Messages.redBold(element.value.toString()));
          formatter.format("    Bound at: ");
          formatElement(element, formatter);
        }
      }
    }
  }
  formatter.format("\n%s\n", Messages.bold("Multibinder declared at:"));
  // Multibinder source includes the key of the set. Filter it out since it's not useful in the
  // printed error stack.
  List<Object> filteredSource =
      getSources().stream()
          .filter(
              source -> {
                if (source instanceof Dependency) {
                  return !((Dependency<?>) source).getKey().equals(setKey);
                }
                return true;
              })
          .collect(Collectors.toList());
  ErrorFormatter.formatSources(filteredSource, formatter);
}