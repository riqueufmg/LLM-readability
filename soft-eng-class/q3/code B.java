@Override
String typeName(Type type) {
	try {
		Method getTypeName = Type.class.getMethod("getTypeName");
		return (String) getTypeName.invoke(type);
	} catch (ReflectiveOperationException e) {
		throw new AssertionError("Type.getTypeName should be available in Java 8", e);
	}
}