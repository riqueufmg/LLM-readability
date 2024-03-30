public WithFlexibleName withAuxiliaryField(FieldDescription.Token token, Object value) {
    Map<String, Object> auxiliaryFields = new HashMap<String, Object>(this.auxiliaryFields);
    Object previous = auxiliaryFields.put(token.getName(), value);
    if (previous != null) {
        if (previous == value) {
            return this;
        } else {
            throw new IllegalStateException("Field " + token.getName()
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
            new LoadedTypeInitializer.Compound(loadedTypeInitializer, new LoadedTypeInitializer.ForStaticField(token.getName(), value)),
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