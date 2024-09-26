@Override
public Compilation getCompilationByAddress(AssemblyMethod asmMethod) {
    Compilation result = null;

    String entryAddress = asmMethod.getEntryAddress();
    String nativeAddress = asmMethod.getNativeAddress();

    for (Compilation compilation : compilations) {
        if ((entryAddress!= null && entryAddress.equals(compilation.getEntryAddress())) 
                || (nativeAddress!= null && nativeAddress.equals(compilation.getNativeAddress()))) {
            result = compilation;
            break;
        }
    }

    return result;
}