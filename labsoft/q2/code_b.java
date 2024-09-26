public static void buildClassGenerics(String line, ClassBC classBytecode)
	{
		StringBuilder keyBuilder = new StringBuilder();
		StringBuilder valBuilder = new StringBuilder();

		boolean inKey = false;
		boolean inVal = false;

		for (int i = 0; i < line.length(); i++)
		{
			char c = line.charAt(i);

			if (c == C_OPEN_ANGLE)
			{
				inKey = true;
				inVal = false;
			}
			else if (c == C_COLON && inKey)
			{
				inKey = false;
				inVal = true;
			}
			else if (c == C_SEMICOLON && !inKey && inVal)
			{
				String key = keyBuilder.toString();
				String val = valBuilder.toString();

				if (val.length() > 0)
				{
					val = val.substring(1); // string leading 'L'
					val = val.replace(S_SLASH, S_DOT);
				}

				classBytecode.addGenericsMapping(key, val);

				keyBuilder.setLength(0);
				valBuilder.setLength(0);

				inKey = true;
				inVal = false;
			}
			else if (inKey)
			{
				keyBuilder.append(c);
			}
			else if (inVal)
			{
				valBuilder.append(c);
			}
		}

		if (!inKey && inVal)
		{
			String key = keyBuilder.toString();
			String val = valBuilder.toString();

			if (val.length() > 0)
			{
				val = val.substring(1); // string leading 'L'
				val = val.replace(S_SLASH, S_DOT);
			}

			classBytecode.addGenericsMapping(key, val);
			keyBuilder.setLength(0);
			valBuilder.setLength(0);

			inKey = false;
			inVal = false;
		}
	}