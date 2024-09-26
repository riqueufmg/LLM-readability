public Generic get(int index) {
    // Avoid resolution of interface bound type unless a type annotation can be possibly resolved.
    Map<String, List<AnnotationToken>> annotationTokens;

    if (this.annotationTokens.containsKey(index) || this.annotationTokens.containsKey(index + 1)) {
        int key = boundTypeTokens.get(0).isPrimaryBound(typePool)? index : index + 1;
        annotationTokens = this.annotationTokens.get(key);
    } else {
        annotationTokens = Collections.emptyMap();
    }

    return boundTypeTokens.get(index).toGenericType(typePool,
            typeVariableSource,
            EMPTY_TYPE_PATH,
            annotationTokens == null? Collections.emptyMap() : annotationTokens);
}