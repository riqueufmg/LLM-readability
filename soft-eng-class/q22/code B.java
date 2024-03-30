public static String asString(Document doc, @Nullable Map<String, String> properties) {
    try {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        if (properties != null)
            transformer.setOutputProperties(propertiesFromMap(properties));

        if (doc.getDoctype() != null) {
            DocumentType doctype = doc.getDoctype();
            if (!StringUtil.isBlank(doctype.getPublicId()))
                transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
            if (!StringUtil.isBlank(doctype.getSystemId()))
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
                // handle <!doctype html> for legacy dom. TODO: nicer if <!doctype html>
            else if (doctype.getName().equalsIgnoreCase("html")
                && StringUtil.isBlank(doctype.getPublicId())
                && StringUtil.isBlank(doctype.getSystemId()))
                // TODO: Handle <!doctype html> for legacy dom in a nicer way
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "about:legacy-compat");
        }

        transformer.transform(domSource, result);
        return writer.toString();

    } catch (TransformerException e) {
        throw new IllegalStateException(e);
    }
}