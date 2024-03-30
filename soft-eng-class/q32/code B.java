private static boolean compareTypeEquality(String memberTypeName, String inMspTypeName, Map<String, String> genericsMap) {
	String mspTypeName = inMspTypeName;

	if (memberTypeName != null && memberTypeName.equals(mspTypeName)) {
		return true;
	} else if (mspTypeName != null) {
		String mspTypeNameWithoutArray = getParamTypeWithoutArrayBrackets(mspTypeName);

		String genericSubstitution = genericsMap.get(mspTypeNameWithoutArray);

		if (genericSubstitution != null) {
			mspTypeName = mspTypeName.replace(mspTypeNameWithoutArray, genericSubstitution);

			if (memberTypeName != null && memberTypeName.equals(mspTypeName)) {
				return true;
			}
		}
	}

	return false;
}