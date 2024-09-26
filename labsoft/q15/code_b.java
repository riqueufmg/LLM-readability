public static boolean isJournalForCompile2NativeMember(Tag tag) {
    if (tag!= null && TAG_NMETHOD.equals(tag.getName()) && C2N.equalsIgnoreCase(tag.getAttributes().get(ATTR_COMPILE_KIND))) {
        return true;
    }
    return false;
}