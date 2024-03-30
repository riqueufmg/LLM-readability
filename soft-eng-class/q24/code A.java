private void doInsertElement(Element el, @Nullable Token token) {
    if (el.tag().isFormListed() && formElement != null)
        formElement.addElement(el); // connect form controls to their form element

    // in HTML, the xmlns attribute if set must match what the parser set the tag's namespace to
    if (el.hasAttr("xmlns") && !el.attr("xmlns").equals(el.tag().namespace()))
        error("Invalid xmlns attribute [%s] on tag [%s]", el.attr("xmlns"), el.tagName());

    if (isFosterInserts() && StringUtil.inSorted(currentElement().normalName(), InTableFoster))
        insertInFosterParent(el);
    else
        currentElement().appendChild(el);

    push(el);
}