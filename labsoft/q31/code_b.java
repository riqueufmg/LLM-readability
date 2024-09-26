private static BytecodeSection getNextSection(BytecodeSection currentSection, final String line) {
    BytecodeSection nextSection = null;

    if (line == null || line.trim().isEmpty() || (currentSection!= BytecodeSection.CODE && "}".equals(line.trim()))) {
        nextSection = BytecodeSection.NONE;
    } else {
        for (Map.Entry<String, BytecodeSection> entry : sectionLabelMap.entrySet()) {
            if (line.trim().startsWith(entry.getKey())) {
                nextSection = entry.getValue();
                break;
            }
        }
    }

    return nextSection;
}