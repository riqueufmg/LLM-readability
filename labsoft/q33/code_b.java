@Override
    protected void initialiseParse(Reader input, String baseUri, Parser parser) {
        super.initialiseParse(input, baseUri, parser);

        // this is a bit mucky. todo - probably just create new parser objects to ensure all reset.
        state = HtmlTreeBuilderState.Initial;
        originalState = null;
        baseUriSetFromDoc = false;
        headElement = null;
        formElement = null;
        contextElement = null;
        formattingElements = new ArrayList<>();
        tmplInsertMode = new ArrayList<>();
        pendingTableCharacters = new ArrayList<>();
        emptyEnd = new Token.EndTag(this);
        framesetOk = true;
        fosterInserts = false;
        fragmentParsing = false;
    }