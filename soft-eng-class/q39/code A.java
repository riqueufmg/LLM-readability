@Override
protected FieldDescription resolve(TypeDescription instrumentedType, MethodDescription instrumentedMethod) {
    FieldLocator locator = fieldLocator(instrumentedType);
    FieldLocator.Resolution resolution = name.equals(BEAN_PROPERTY)
            ? FieldLocator.Resolution.Simple.ofBeanAccessor(locator, instrumentedMethod)
            : locator.locate(name);
    if (!resolution.isResolved()) {
        throw new IllegalStateException("Cannot locate field named " + name + " for " + instrumentedType);
    } else {
        return resolution.getField();
    }
}