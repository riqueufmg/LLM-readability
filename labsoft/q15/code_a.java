public static boolean isJournalForCompile2NativeMember(Tag tag)
{
    boolean result = false;

    if (tag!= null && TAG_NMETHOD.equals(tag.getName()) && C2N.equalsIgnoreCase(tag.getAttributes().get(ATTR_COMPILE_KIND)))
    {
        result = true;
    }

    return result;
}