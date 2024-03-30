public Object newInstance(Project project, Class<?> type, Object... argument) {
    try {
        return newInstance.invoke(getObjects.invoke(project), type, argument);
    } catch (IllegalAccessException e) {
        throw new IllegalStateException(e);
    } catch (InvocationTargetException e) {
        Throwable cause = e.getCause();
        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        } else if (cause instanceof Error) {
            throw (Error) cause;
        } else {
            throw new IllegalStateException(e);
        }
    }
}