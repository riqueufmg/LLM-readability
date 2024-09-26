public void visitEnd() {
    for (String observedType : observedTypes) {
        if (visitedInnerTypes.add(observedType)) {
            TypePool.Resolution resolution = typePool.describe(observedType.replace('/', '.'));
            if (resolution.isResolved()) {
                processResolvedType(resolution.resolve());
            } else if (strict) {
                throw new IllegalStateException("Could not locate type for: " + observedType.replace('/', '.'));
            }
        }
    }
    super.visitEnd();
}

private void processResolvedType(TypeDescription typeDescription) {
    if (!filter.matches(typeDescription)) {
        processNestedTypes(typeDescription);
    }
}

private void processNestedTypes(TypeDescription typeDescription) {
    while (typeDescription!= null && typeDescription.isNestedClass()) {
        super.visitInnerClass(typeDescription.getInternalName(),
                typeDescription.isMemberType()
                       ? typeDescription.getDeclaringType().getInternalName()
                        : null,
                typeDescription.isAnonymousType()
                       ? null
                        : typeDescription.getSimpleName(),
                typeDescription.getModifiers());
        typeDescription = getNextEnclosingType(typeDescription);
    }
}

private TypeDescription getNextEnclosingType(TypeDescription typeDescription) {
    try {
        do {
            typeDescription = typeDescription.getEnclosingType();
        } while (typeDescription!= null &&!visitedInnerTypes.add(typeDescription.getInternalName()));
        return typeDescription;
    } catch (RuntimeException exception) {
        if (strict) {
            throw exception;
        } else {
            return null;
        }
    }
}