private void ensureMetaCharsetElement() {
    if (!updateMetaCharset) {
        return;
    }

    OutputSettings.Syntax syntax = outputSettings().syntax();
    String charsetName = charset().displayName();

    if (syntax == OutputSettings.Syntax.html) {
        updateHtmlMetaCharset(charsetName);
    } else if (syntax == OutputSettings.Syntax.xml) {
        updateXmlDeclaration(charsetName);
    }
}

private void updateHtmlMetaCharset(String charsetName) {
    Element metaCharset = selectFirst("meta[charset]");
    if (metaCharset!= null) {
        metaCharset.attr("charset", charsetName);
    } else {
        head().appendElement("meta").attr("charset", charsetName);
    }
    select("meta[name=charset]").remove(); // Remove obsolete elements
}

private void updateXmlDeclaration(String charsetName) {
    Node node = ensureChildNodes().get(0);
    XmlDeclaration decl = getXmlDeclaration(node);
    if (decl == null) {
        decl = new XmlDeclaration("xml", false);
        prependChild(decl);
    }
    decl.attr("version", "1.0");
    decl.attr("encoding", charsetName);
}

private XmlDeclaration getXmlDeclaration(Node node) {
    if (node instanceof XmlDeclaration) {
        XmlDeclaration decl = (XmlDeclaration) node;
        if (decl.name().equals("xml")) {
            return decl;
        }
    }
    return null;
}