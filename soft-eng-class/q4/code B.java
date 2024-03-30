private ArrayTable(Iterable<? extends R> rowKeys, Iterable<? extends C> columnKeys) {
    this.rowList = ImmutableList.copyOf(rowKeys);
    this.columnList = ImmutableList.copyOf(columnKeys);
    checkArgument(rowList.isEmpty() == columnList.isEmpty());

    // Create index maps for row keys and column keys
    rowKeyToIndex = Maps.indexMap(rowList);
    columnKeyToIndex = Maps.indexMap(columnList);

    @SuppressWarnings("unchecked")
    @Nullable
    V[][] tmpArray = (@Nullable V[][]) new Object[rowList.size()][columnList.size()];
    array = tmpArray;
    // Initialize the array with null values
    eraseAll();
}