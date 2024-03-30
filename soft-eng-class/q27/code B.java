public Node wrap(String html) {
    Validate.notEmpty(html);

    // Parse context - parent (because wrapping), this, or null
    Element context = null;
    if (parentNode != null && parentNode instanceof Element) {
        context = (Element) parentNode;
    } else if (this instanceof Element) {
        context = (Element) this;
    }

    List<Node> wrapChildren = NodeUtils.parser(this).parseFragmentInput(html, context, baseUri());
    Node wrapNode = wrapChildren.get(0);
    if (!(wrapNode instanceof Element)) { // nothing to wrap with; noop
        return this;
    }

    Element wrap = (Element) wrapNode;
    Element deepest = getDeepChild(wrap);
    if (parentNode != null) {
        parentNode.replaceChild(this, wrap);
    }
    deepest.addChildren(this); // side effect of tricking wrapChildren to lose first

    // remainder (unbalanced wrap, like <div></div><p></p> -- The <p> is remainder
    if (wrapChildren.size() > 0) {
        for (int i = 0; i < wrapChildren.size(); i++) {
            Node remainder = wrapChildren.get(i);
            // if no parent, this could be the wrap node, so skip
            if (wrap == remainder) {
                continue;
            }

            if (remainder.parentNode != null) {
                remainder.parentNode.removeChild(remainder);
            }
            wrap.after(remainder);
        }
    }
    return this;
}