public static FilterResult filter(NodeFilter filter, Node root) {
    Node node = root;
    int depth = 0;

    while (node != null) {
        FilterResult result = filter.head(node, depth);
        if (result == FilterResult.STOP)
            return result;
        // Descend into child nodes:
        if (result == FilterResult.CONTINUE && node.childNodeSize() > 0) {
            node = node.childNode(0);
            ++depth;
            continue;
        }
        // No siblings, move upwards:
        while (true) {
            assert node != null; // depth > 0, so has parent
            if (!(node.nextSibling() == null && depth > 0)) break;
            // 'tail' current node:
            if (result == FilterResult.CONTINUE || result == FilterResult.SKIP_CHILDREN) {
                result = filter.tail(node, depth);
                if (result == FilterResult.STOP)
                    return result;
            }
            Node prev = node; // In case we need to remove it below.
            node = node.parentNode();
            depth--;
            if (result == FilterResult.REMOVE)
                prev.remove(); // Remove AFTER finding parent.
            result = FilterResult.CONTINUE; // Parent was not pruned.
        }
        // 'tail' current node, then proceed with siblings:
        if (result == FilterResult.CONTINUE || result == FilterResult.SKIP_CHILDREN) {
            result = filter.tail(node, depth);
            if (result == FilterResult.STOP)
                return result;
        }
        if (node == root)
            return result;
        Node prev = node; // In case we need to remove it below.
        node = node.nextSibling();
        if (result == FilterResult.REMOVE)
            prev.remove(); // Remove AFTER finding sibling.
    }
    // root == null?
    return FilterResult.CONTINUE;
}