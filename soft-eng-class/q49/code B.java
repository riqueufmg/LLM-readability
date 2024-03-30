private void enhanceConstructor(ClassWriter cw, Constructor<?> constructor) {
    String descriptor = Type.getConstructorDescriptor(constructor);
    String enhancedDescriptor = '(' + HANDLERS_DESCRIPTOR + descriptor.substring(1);

    String handlersDescriptor = HANDLERS_DESCRIPTOR; // Extracted duplicated string literal

    MethodVisitor mv =
        cw.visitMethod(PUBLIC, "<init>", enhancedDescriptor, null, exceptionNames(constructor));

    mv.visitCode();

    mv.visitVarInsn(ALOAD, 0);
    mv.visitInsn(DUP);
    mv.visitVarInsn(ALOAD, 1);
    // store handlers before invoking the superclass constructor (JVM allows this)
    mv.visitFieldInsn(PUTFIELD, proxyName, HANDLERS_NAME, handlersDescriptor); // Replaced HANDLERS_DESCRIPTOR with handlersDescriptor

    int slot = 2;
    for (Class<?> parameterType : constructor.getParameterTypes()) {
      slot += loadArgument(mv, parameterType, slot);
    }

    mv.visitMethodInsn(INVOKESPECIAL, hostName, "<init>", descriptor, false);

    mv.visitInsn(RETURN);
    mv.visitMaxs(0, 0);
    mv.visitEnd();
}