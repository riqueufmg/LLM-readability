public static Safelist basic() {
    String[] commonTags = {"a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
        "i", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub",
        "sup", "u", "ul"};

    Safelist safelist = new Safelist()
        .addTags(commonTags)
        .addAttributes("a", "href")
        .addAttributes("blockquote", "cite")
        .addAttributes("q", "cite")
        .addProtocols("a", "href", "ftp", "http", "https", "mailto")
        .addProtocols("blockquote", "cite", "http", "https")
        .addProtocols("cite", "cite", "http", "https")
        .addEnforcedAttribute("a", "rel", "nofollow");

    return safelist;
}