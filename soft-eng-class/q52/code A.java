@Override
public Boolean visit(UntargettedBinding<? extends T> untargetted) {
  prepareBinding();

  // Error: Missing implementation.
  // Example: bind(Date.class).annotatedWith(Red.class);
  // We can't assume abstract types aren't injectable. They may have an
  // @ImplementedBy annotation or something.
  if (key.getAnnotationType() != null) {
    errors.missingImplementationWithHint(key, injector);
    putBinding(invalidBinding(injector, key, source));
    return true;
  }

  // Queue up the creationListener for notify until after bindings are processed.
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