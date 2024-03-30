public WithFlexibleName withAuxiliaryField(FieldDescription.Token token, Object value) {
    String fieldName = token.getName(); // Extract the duplicated string literal into a variable
    Map<String, Object> auxiliaryFields = new HashMap<String, Object>(this.auxiliaryFields);
    Object previous = auxiliaryFields.put(fieldName, value); // Use the variable instead of the duplicated string literal
    if (previous != null) {
        if (previous == value) {
            return this;
        } else {
            throw new IllegalStateException("Field " + fieldName // Use the variable instead of the duplicated string literal
                    + " for " + this
                    + " already mapped to " + previous
                    + " and not " + value);
        }
    }
    return new Default(name,
            modifiers,
            superClass,
            typeVariables,
            interfaceTypes,
            CompoundList.of(fieldTokens, token.accept(Generic.Visitor.Substitutor.ForDetachment.of(this))),
            auxiliaryFields,
            methodTokens,
            recordComponentTokens,
            annotationDescriptions,
            typeInitializer,
            new LoadedTypeInitializer.Compound(loadedTypeInitializer, new LoadedTypeInitializer.ForStaticField(fieldName, value)), // Use the variable instead of the duplicated string literal
            declaringType,
            enclosingMethod,
            enclosingType,
            declaredTypes,
            permittedSubclasses,
            anonymousClass,
            localClass,
            record,
            nestHost,
            nestMembers);
}