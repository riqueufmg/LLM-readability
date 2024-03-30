public static FilterResult filter(NodeFilter filter, Node root) {
    Node node = root;
    int depth = 0;

    while (node != null) {
        FilterResult result = filter.head(node, depth);
        if (result == FilterResult.STOP) {
            return result;
        }

        if (result == FilterResult.CONTINUE && node.childNodeSize() > 0) {
            node = node.childNode(0);
            depth++;
            continue;
        }

        while (node.nextSibling() == null && depth > 0) {
            if (result == FilterResult.CONTINUE || result == FilterResult.SKIP_CHILDREN) {
                result = filter.tail(node, depth);
                if (result == FilterResult.STOP) {
                    return result;
                }
            }
            Node prev = node;
            node = node.parentNode();
            depth--;
            if (result == FilterResult.REMOVE) {
                prev.remove();
            }
            result = FilterResult.CONTINUE;
        }

        if (result == FilterResult.CONTINUE || result == FilterResult.SKIP_CHILDREN) {
            result = filter.tail(node, depth);
            if (result == FilterResult.STOP) {
                return result;
            }
        }

        if (node == root) {
            return result;
        }

        Node prev = node;
        node = node.nextSibling();
        if (result == FilterResult.REMOVE) {
            prev.remove();
        }
    }

    return FilterResult.CONTINUE;
}