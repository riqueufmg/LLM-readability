public int getLogLineIndex(String input)
{
	Matcher matcher = PATTERN_LOG_SIGNATURE.matcher(input);

	int result = -1;

	if (!input.contains("made not entrant") && matcher.find())
	{
		int count = matcher.groupCount();

		if (count >= 1)
		{
			result = matcher.group(1).length();
		}
	}

	return result;
}