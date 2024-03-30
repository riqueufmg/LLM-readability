private static final String UNEXPECTED_MODIFIERS = "Unexpected modifiers: ";

public Visibility getVisibility() {
    int modifiers = getModifiers();
    switch (modifiers & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED | Opcodes.ACC_PRIVATE)) {
        case Opcodes.ACC_PUBLIC:
            return Visibility.PUBLIC;
        case Opcodes.ACC_PROTECTED:
            return Visibility.PROTECTED;
        case EMPTY_MASK:
            return Visibility.PACKAGE_PRIVATE;
        case Opcodes.ACC_PRIVATE:
            return Visibility.PRIVATE;
        default:
            throw new IllegalStateException(UNEXPECTED_MODIFIERS + modifiers);
    }
}