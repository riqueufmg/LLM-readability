public static Method[] getDeclaredMethods(Class<?> type) {
    return Arrays.stream(type.getDeclaredMethods())
       .sorted(
            Comparator.comparing(Method::getName)
               .thenComparing(Method::getReturnType, Comparator.comparing(Class::getName))
               .thenComparing(
                    method -> Arrays.asList(method.getParameterTypes()),
                    Ordering.<Class<?>>from(Comparator.comparing(Class::getName))
                       .lexicographical()))
       .toArray(Method[]::new);
}