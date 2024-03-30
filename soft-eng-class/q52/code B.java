@Override
public Boolean visit(UntargettedBinding<? extends T> untargetted) {
  prepareBinding();

  if (key.getAnnotationType() != null) {
    errors.missingImplementationWithHint(key, injector);
    putBinding(invalidBinding(injector, key, source));
    return true;
  }

  try {
    BindingImpl<T> binding =
        injector.createUninitializedBinding(
            key,
            scoping,
            source,
            errors,
            false,
            processedBindingData::addCreationListener);
    scheduleInitialization(binding);
    putBinding(binding);
  } catch (ErrorsException e) {
    errors.merge(e.getErrors());
    putBinding(invalidBinding(injector, key, source));
  }

  return true;
}