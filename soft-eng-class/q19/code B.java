private void ensureMetaCharsetElement() {
    if (updateMetaCharset) {
        OutputSettings.Syntax syntax = outputSettings().syntax();

        if (syntax == OutputSettings.Syntax.html) {
            handleHtmlSyntax();
        } else if (syntax == OutputSettings.Syntax.xml) {
            handleXmlSyntax();
        }
    }
}

private void handleHtmlSyntax() {
    Element metaCharset = selectFirst("meta[charset]");
    if (metaCharset != null) {
        metaCharset.attr("charset", charset().displayName());
    } else {
        head().appendElement("meta").attr("charset", charset().displayName());
    }
    select("meta[name=charset]").remove(); // Remove obsolete elements
}

private void handleXmlSyntax() {
    Node node = ensureChildNodes().get(0);
    if (node instanceof XmlDeclaration) {
        XmlDeclaration decl = (XmlDeclaration) node;
        if (decl.name().equals("xml")) {
            decl.attr("encoding", charset().displayName());
            if (decl.hasAttr("version"))
                decl.attr("version", "1.0");
        } else {
            decl = new XmlDeclaration("xml", false);
            decl.attr("version", "1.0");
            decl.attr("encoding", charset().displayName());
            prependChild(decl);
        }
    } else {
        XmlDeclaration decl = new XmlDeclaration("xml", false);
        decl.attr("version", "1.0");
        decl.attr("encoding", charset().displayName());
        prependChild(decl);
    }
}