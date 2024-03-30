static void prepareBuiltInConverters(InjectorImpl injector) {
  // Configure type converters.
  convertToPrimitiveType(injector, int.class, Integer.class);
  convertToPrimitiveType(injector, long.class, Long.class);
  convertToPrimitiveType(injector, boolean.class, Boolean.class);
  convertToPrimitiveType(injector, byte.class, Byte.class);
  convertToPrimitiveType(injector, short.class, Short.class);
  convertToPrimitiveType(injector, float.class, Float.class);
  convertToPrimitiveType(injector, double.class, Double.class);

  convertToClass(
      injector,
      Character.class,
      new TypeConverter() {
        @Override
        public Object convert(String value, TypeLiteral<?> toType) {
          value = value.trim();
          if (value.length() != 1) {
            throw new RuntimeException("Length != 1.");
          }
          return value.charAt(0);
        }

        @Override
        public String toString() {
          return "TypeConverter<Character>";
        }
      });

  convertToClasses(
      injector,
      Matchers.subclassesOf(Enum.class),
      new TypeConverter() {
        @SuppressWarnings("rawtypes") // Unavoidable, only way to use Enum.valueOf
        @Override
        public Object convert(String value, TypeLiteral<?> toType) {
          return Enum.valueOf((Class) toType.getRawType(), value);
        }

        @Override
        public String toString() {
          return "TypeConverter<E extends Enum<E>>";
        }
      });

  internalConvertToTypes(
      injector,
      new AbstractMatcher<TypeLiteral<?>>() {
        @Override
        public boolean matches(TypeLiteral<?> typeLiteral) {
          return typeLiteral.getRawType() == Class.class;
        }

        @Override
        public String toString() {
          return "Class<?>";
        }
      },
      new TypeConverter() {
        @Override
        public Object convert(String value, TypeLiteral<?> toType) {
          try {
            return Class.forName(value);
          } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
          }
        }

        @Override
        public String toString() {
          return "TypeConverter<Class<?>>";
        }
      });
}