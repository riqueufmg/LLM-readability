private static final String FIELD_NAME_ERROR_MESSAGE = "Cannot locate field named ";

@Override
protected FieldDescription resolve(TypeDescription instrumentedType, MethodDescription instrumentedMethod) {
    FieldLocator locator = fieldLocator(instrumentedType);
    FieldLocator.Resolution resolution = name.equals(BEAN_PROPERTY)
            ? FieldLocator.Resolution.Simple.ofBeanAccessor(locator, instrumentedMethod)
            : locator.locate(name);
    if (!resolution.isResolved()) {
        throw new IllegalStateException(FIELD_NAME_ERROR_MESSAGE + name + " for " + instrumentedType);
    } else {
        return resolution.getField();
    }
}