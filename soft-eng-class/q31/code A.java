public static void visitParseTagsOfCompilation(Compilation compilation, ICompilationVisitable visitable)
			throws LogParseException
{
	if (compilation != null)
	{
		Task tagTask = compilation.getTagTask();

		if (tagTask == null)
		{
			if (!compilation.isC2N())
			{

				String compilationID = compilation.getCompileID();

				logger.warn("No Task found in Compilation {}", compilationID);
				logger.warn("Compilation tags for {}\n{}", compilationID, compilation.toStringVerbose());
			}
		}
		else
		{
			IParseDictionary parseDictionary = tagTask.getParseDictionary();

			Tag parsePhase = getParsePhase(tagTask);

			if (parsePhase != null)
			{
				List<Tag> parseTags = parsePhase.getNamedChildren(TAG_PARSE);

				if (DEBUG_LOGGING)
				{
					logger.debug("About to visit {} parse tags of <task>", parseTags.size());
				}

				for (Tag parseTag : parseTags)
				{
					visitable.visitTag(parseTag, parseDictionary);
				}
			}
		}
	}
	else
	{
		logger.warn("Compilation is null");
	}
}