public static Safelist basic() {
    return new Safelist()
            .addTags(
                    "a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
                    "i", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub",
                    "sup", "u", "ul")

            .addAttributes("a", "href")
            .addAttributes("blockquote", "cite")
            .addAttributes("q", "cite")

            .addProtocols("a", "href", "ftp", "http", "https", "mailto")
            .addProtocols("blockquote", "cite", "http", "https")
            .addProtocols("cite", "cite", "http", "https")

            .addEnforcedAttribute("a", "rel", "nofollow")
            ;

}
