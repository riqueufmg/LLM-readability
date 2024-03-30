@Override
protected byte[] generateGlue(Collection<Executable> members) {
ClassWriter cw = new ClassWriter(COMPUTE_MAXS);
MethodVisitor mv;

// target Java8 because that's all we need for the generated trampoline code
cw.visit(V1_8, PUBLIC | FINAL | ACC_SUPER, proxyName, null, "java/lang/Object", FAST_CLASS_API);
cw.visitSource(GENERATED_SOURCE, null);

// this shared field contains the constructor handle adapted to look like an invoker table
cw.visitField(PUBLIC | STATIC | FINAL, INVOKERS_NAME, INVOKERS_DESCRIPTOR, null, null)
    .visitEnd();

setupInvokerTable(cw);

cw.visitField(PRIVATE | FINAL, "index", "I", null, null).visitEnd();

// fast-class constructor that takes an index and binds it
mv = cw.visitMethod(PUBLIC, "<init>", "(I)V", null, null);
mv.visitCode();
mv.visitVarInsn(ALOAD, 0);
mv.visitInsn(DUP);
mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
mv.visitVarInsn(ILOAD, 1);
mv.visitFieldInsn(PUTFIELD, proxyName, "index", "I");
mv.visitInsn(RETURN);
mv.visitMaxs(0, 0);
mv.visitEnd();

// fast-class invoker function that takes a context object and argument array
mv = cw.visitMethod(PUBLIC, "apply", RAW_INVOKER_DESCRIPTOR, null, null);
mv.visitCode();
// combine bound index with context object and argument array
mv.visitVarInsn(ALOAD, 0);
mv.visitFieldInsn(GETFIELD, proxyName, "index", "I");
mv.visitVarInsn(ALOAD, 1);
mv.visitVarInsn(ALOAD, 2);
mv.visitTypeInsn(CHECKCAST, OBJECT_ARRAY_TYPE);
// call into the shared trampoline
mv.visitMethodInsn(INVOKESTATIC, proxyName, TRAMPOLINE_NAME, TRAMPOLINE_DESCRIPTOR, false);
mv.visitInsn(ARETURN);
mv.visitMaxs(0, 0);
mv.visitEnd();

generateTrampoline(cw, members);

cw.visitEnd();
return cw.toByteArray();
}