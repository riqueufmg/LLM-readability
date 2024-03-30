public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
  // require an owner type if the raw type needs it
  ensureOwnerType(ownerType, rawType);

  this.ownerType = ownerType == null ? null : canonicalize(ownerType);
  this.rawType = canonicalize(rawType);
  int providedArgumentLength = typeArguments.length;
  Type[] clonedTypeArguments = typeArguments.clone();
  int validArgLength = providedArgumentLength;
  if (this.rawType instanceof Class) {
    Class<?> klass = (Class) this.rawType;
    int classArgumentLength = klass.getTypeParameters().length;
    // TODO(b/163147654): change following if condition from < to =
    if (providedArgumentLength < classArgumentLength) {
      throw new IllegalArgumentException(
          "Length of provided type arguments is less than length of required parameters for"
              + " class:"
              + klass.getName()
              + " provided type argument length:"
              + providedArgumentLength
              + " length of class parameters:"
              + classArgumentLength);
    } else if (providedArgumentLength > classArgumentLength) {
      validArgLength = classArgumentLength;
    }
  }

  this.typeArguments = new Type[validArgLength];
  for (int t = 0; t < validArgLength; t++) {
    checkNotNull(clonedTypeArguments[t], "type parameter");
    checkNotPrimitive(clonedTypeArguments[t], "type parameters");
    this.typeArguments[t] = canonicalize(clonedTypeArguments[t]);
  }
}