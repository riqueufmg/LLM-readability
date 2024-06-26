private void visitTagEliminateAllocation(Tag tagEliminateAllocation, IParseDictionary parseDictionary)
{
	List<Tag> childrenJVMS = tagEliminateAllocation.getNamedChildren(TAG_JVMS);

	String typeID = tagEliminateAllocation.getAttributes().get(ATTR_TYPE);

	String typeOrKlassName = null;

	if (typeID != null)
	{
		typeOrKlassName = ParseUtil.lookupType(typeID, parseDictionary);

		if (typeOrKlassName != null)
		{
			for (Tag tagJVMS : childrenJVMS)
			{
				Map<String, String> tagJVMSAttributes = tagJVMS.getAttributes();

				String attrBCI = tagJVMSAttributes.get(ATTR_BCI);

				int bciValue = 0;

				if (attrBCI != null)
				{
					try
					{
						bciValue = Integer.parseInt(attrBCI);
					}
					catch (NumberFormatException nfe)
					{
						logger.error("Couldn't parse bci attribute {} tag {}", attrBCI, tagJVMS.toString(true));
						continue;
					}
				}
				else
				{
					logger.error("Missing bci attribute on tag {}", tagJVMS.toString(true));
				}

				String methodID = tagJVMSAttributes.get(ATTR_METHOD);

				BCIOpcodeMap bciOpcodeMap = parseDictionary.getBCIOpcodeMap(methodID);
				
				//logger.info("method {} {} {}", methodID, parseDictionary.getParseMethod(), bciOpcodeMap.entrySet());

				if (CompilationUtil.memberMatchesMethodID(currentMember, methodID, parseDictionary))
				{
					storeEliminatedAllocation(currentMember, bciValue, typeOrKlassName, bciOpcodeMap);
				}
				else
				{
					processAnnotationsForInlinedMethods(tagJVMS, parseDictionary, bciValue, typeOrKlassName, bciOpcodeMap);
				}
			}
		}
		else
		{
			logger.error("Unknown type attribute {} on tag {}", typeID, tagEliminateAllocation.toString(true));
		}
	}
	else
	{
		logger.error("Missing type attribute on tag {}", tagEliminateAllocation.toString(true));
	}
}

private void processAnnotationsForInlinedMethods(Tag tagJVMS, IParseDictionary parseDictionary, int bciValue, String typeOrKlassName, BCIOpcodeMap bciOpcodeMap)
{
	if (processAnnotationsForInlinedMethods)
	{
		IMetaMember inlinedMember = findMemberForInlinedMethod(tagJVMS, parseDictionary);

		if (inlinedMember != null)
		{
			storeEliminatedAllocation(inlinedMember, bciValue, typeOrKlassName, bciOpcodeMap);
		}
		else
		{
			unhandledTags.add(tagJVMS);
		}
	}
}