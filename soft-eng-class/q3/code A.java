@Override
String typeName(Type type) {
  try {
    Method getTypeName = Type.class.getMethod("getTypeName");
    return (String) getTypeName.invoke(type);
  } catch (NoSuchMethodException e) {
    throw new AssertionError("Type.getTypeName should be available in Java 8");
    /*
     * Do not merge the 2 catch blocks below. javac would infer a type of
     * ReflectiveOperationException, which Animal Sniffer would reject. (Old versions of
     * Android don't *seem* to mind, but there might be edge cases of which we're unaware.)
     */
  } catch (InvocationTargetException e) {
    throw new RuntimeException(e);
  } catch (IllegalAccessException e) {
    throw new RuntimeException(e);
  }
}