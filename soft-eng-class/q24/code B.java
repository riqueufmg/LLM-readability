private static final String XMLNS_ATTRIBUTE = "xmlns";
    
private void doInsertElement(Element el, @Nullable Token token) {
    if (el.tag().isFormListed() && formElement != null)
        formElement.addElement(el); // connect form controls to their form element

    // in HTML, the xmlns attribute if set must match what the parser set the tag's namespace to
    if (el.hasAttr(XMLNS_ATTRIBUTE) && !el.attr(XMLNS_ATTRIBUTE).equals(el.tag().namespace()))
        error("Invalid %s attribute [%s] on tag [%s]", XMLNS_ATTRIBUTE, el.attr(XMLNS_ATTRIBUTE), el.tagName());

    if (isFosterInserts() && StringUtil.inSorted(currentElement().normalName(), InTableFoster))
        insertInFosterParent(el);
    else
        currentElement().appendChild(el);

    push(el);
}