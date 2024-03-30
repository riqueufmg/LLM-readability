private ArrayTable(Iterable<? extends R> rowKeys, Iterable<? extends C> columnKeys) {
    this.rowList = ImmutableList.copyOf(rowKeys);
    this.columnList = ImmutableList.copyOf(columnKeys);
    checkArgument(rowList.isEmpty() == columnList.isEmpty());

    /*
     * TODO(jlevy): Support only one of rowKey / columnKey being empty? If we
     * do, when columnKeys is empty but rowKeys isn't, rowKeyList() can contain
     * elements but rowKeySet() will be empty and containsRow() won't
     * acknowledge them.
     */
    rowKeyToIndex = Maps.indexMap(rowList);
    columnKeyToIndex = Maps.indexMap(columnList);

    @SuppressWarnings("unchecked")
    @Nullable
    V[][] tmpArray = (@Nullable V[][]) new Object[rowList.size()][columnList.size()];
    array = tmpArray;
    // Necessary because in GWT the arrays are initialized with "undefined" instead of null.
    eraseAll();
}