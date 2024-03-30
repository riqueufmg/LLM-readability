private static <T> void convertToPrimitiveType(
    InjectorImpl injector, Class<T> primitiveType, final Class<T> wrapperType) {
  try {
    final Method parser =
        wrapperType.getMethod("parse" + capitalize(primitiveType.getName()), String.class);

    TypeConverter typeConverter =
        new TypeConverter() {
          @Override
          public Object convert(String value, TypeLiteral<?> toType) {
            try {
              return parser.invoke(null, value);
            } catch (IllegalAccessException e) {
              throw new AssertionError(e);
            } catch (InvocationTargetException e) {
              Throwable targetException = e.getTargetException();
              if (targetException instanceof RuntimeException) {
                throw (RuntimeException) targetException;
              } else {
                throw new RuntimeException(targetException.getMessage());
              }
            }
          }

          @Override
          public String toString() {
            return "TypeConverter<" + wrapperType.getSimpleName() + ">";
          }
        };

    convertToClass(injector, wrapperType, typeConverter);
  } catch (NoSuchMethodException e) {
    throw new AssertionError(e);
  }
}