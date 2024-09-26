void writeFooterJavadocTagStart(Token token) {
    // Close any unclosed lists (e.g., <li> without <ul>).
    // TODO(cpovirk): Actually generate </ul>, etc.?
    /*
     * TODO(cpovirk): Also generate </pre> and </table> if appropriate. This is necessary for
     * idempotency in broken Javadoc. (We don't necessarily need that, but full idempotency may be a
     * nice goal, especially if it helps us use a fuzzer to test.) Unfortunately, the writer doesn't
     * currently know which of those tags are open.
     */
    continuingListItemOfInnermostList = false;
    continuingListItemCount.reset();
    continuingListCount.reset();
    /*
     * There's probably no need for this, since its only effect is to disable blank lines in some
     * cases -- and we're doing that already in the footer.
     */
    postWriteModifiedContinuingListCount.reset();

    if (!wroteAnythingSignificant) {
      // Javadoc consists solely of tags. This is frowned upon in general but OK for @Overrides.
    } else if (!continuingFooterTag) {
      // First footer tag after a body tag.
      requestBlankLine();
    } else {
      // Subsequent footer tag.
      continuingFooterTag = false;
      requestNewline();
    }
    writeToken(token);
    continuingFooterTag = true;
  }