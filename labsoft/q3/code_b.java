public static boolean isJournalForCompile2NativeMember(Tag tag)
	{
		boolean result = false;

		if (tag != null)
		{
			String tagName = tag.getName();

			if (TAG_NMETHOD.equals(tagName))
			{
				if (C2N.equalsIgnoreCase(tag.getAttributes().get(ATTR_COMPILE_KIND)))
				{
					result = true;
				}
			}
		}

		return result;
	}