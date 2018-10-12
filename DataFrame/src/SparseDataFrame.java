public class SparseDataFrame extends DataFrame {

    public SparseDataFrame(String[] columnNames, String[] typeNames) {
        throw new UnsupportedClassVersionError("This constructor isn't supported in SparseDataFrame. Use different one");
    }

    public SparseDataFrame(Column[] columns) {
        throw new UnsupportedClassVersionError("This constructor isn't supported in SparseDataFrame. Use different one");
    }

    public SparseDataFrame(String[] columnNames, String typeName, String hidden) {
        for (String columnName : columnNames) {
            this.addColumn( new SparseColumn(columnName, typeName, hidden) );
        }
    }

    public SparseDataFrame(DataFrame dataFrame){

    }

}
