@Override
@SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", justification = "Assuming declaring type for type member.")
public void visitEnd() {
    for (String observedType : observedTypes) {
        if (visitedInnerTypes.add(observedType)) {
            TypePool.Resolution resolution = typePool.describe(observedType.replace('/', '.'));
            if (resolution.isResolved()) {
                TypeDescription typeDescription = resolution.resolve();
                if (!filter.matches(typeDescription)) {
                    processNestedClasses(typeDescription);
                }
            } else if (strict) {
                throw new IllegalStateException("Could not locate type for: " + observedType.replace('/', '.'));
            }
        }
    }
    super.visitEnd();
}

private void processNestedClasses(TypeDescription typeDescription) {
    while (typeDescription != null && typeDescription.isNestedClass()) {
        super.visitInnerClass(typeDescription.getInternalName(),
                typeDescription.isMemberType()
                        ? typeDescription.getDeclaringType().getInternalName()
                        : null,
                typeDescription.isAnonymousType()
                        ? null
                        : typeDescription.getSimpleName(),
                typeDescription.getModifiers());
        try {
            typeDescription = typeDescription.getEnclosingType();
        } catch (RuntimeException exception) {
            if (strict) {
                throw exception;
            } else {
                break;
            }
        }
    }
}