public static Method[] getDeclaredMethods(Class<?> type) {
return Arrays.stream(type.getDeclaredMethods())
    .sorted(
        Comparator.comparing(Method::getName)
            .thenComparing(Method::getReturnType, Comparator.comparing(Class::getName))
            .thenComparing(
                method -> Arrays.asList(method.getParameterTypes()),
                // Use lexicographical ordering for parameter types.
                Ordering.<Class<?>>from(Comparator.comparing(Class::getName))
                    .lexicographical()))
    .toArray(Method[]::new);
}