private Elements siblings(@Nullable String query, boolean next, boolean all) {
    Elements els = new Elements();
    Evaluator eval = query != null? QueryParser.parse(query) : null;
    for (Element e : this) {
        do {
            Element sib = next ? e.nextElementSibling() : e.previousElementSibling();
            if (sib == null) break;
            if (eval == null)
                els.add(sib);
            else if (sib.is(eval))
                els.add(sib);
            e = sib;
        } while (all);
    }
    return els;
}