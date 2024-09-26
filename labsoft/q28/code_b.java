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
          if (value.length()!= 1) {
            throw new IllegalArgumentException("Character value must be a single character, but was: " + value);
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
          try {
            return Enum.valueOf((Class) toType.getRawType(), value);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid enum value: " + value, e);
          }
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
            throw new RuntimeException("Class not found: " + value, e);
          }
        }

        @Override
        public String toString() {
          return "TypeConverter<Class<?>>";
        }
      });
}