public Generic get(int index) {
                                    // Avoid resolution of interface bound type unless a type annotation can be possibly resolved.
                                    Map<String, List<AnnotationToken>> annotationTokens = !this.annotationTokens.containsKey(index) && !this.annotationTokens.containsKey(index + 1)
                                            ? Collections.<String, List<AnnotationToken>>emptyMap()
                                            : this.annotationTokens.get(index + (boundTypeTokens.get(0).isPrimaryBound(typePool) ? 0 : 1));
                                    return boundTypeTokens.get(index).toGenericType(typePool,
                                            typeVariableSource,
                                            EMPTY_TYPE_PATH,
                                            annotationTokens == null
                                                    ? Collections.<String, List<AnnotationToken>>emptyMap()
                                                    : annotationTokens);
                                }