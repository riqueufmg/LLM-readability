private static BytecodeSection getNextSection(BytecodeSection currentSection, final String line)
    {
        BytecodeSection nextSection = null;

        if (line != null)
        {
            if (line.isEmpty())
            {
                nextSection = BytecodeSection.NONE;
            }
            else if ( currentSection != BytecodeSection.CODE &&  "}".equals(line.trim()))
            {
                nextSection = BytecodeSection.NONE;
            }

            for (Map.Entry<String, BytecodeSection> entry : sectionLabelMap.entrySet())
            {
                if (line.trim().startsWith(entry.getKey()))
                {
                    nextSection = entry.getValue();
                    break;
                }
            }
        }

        return nextSection;
    }