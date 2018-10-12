import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class DataFrame {

    private ArrayList<Column> columns;
    private int numberOfColumns;

    /**
     * Set to true when there's made a shallow copy of DataFrame without all the original columns and then blocks anyone
     * from from adding new data in order to prevent the corruption of original DataFrame all the columns is in.
     */
    private boolean frozen = false;

    /**
     * Default constructor. Given arrays must be of equal lengths.
     * @param columnNames Names of columns to create.
     * @param typeNames Classes of objects to store in columns. Class names must contain full package names.
     */
    public DataFrame(String[] columnNames, String[] typeNames) {
        if(columnNames.length != typeNames.length)
            throw new IllegalArgumentException("Name and Class arrays lengths don't match up");

        this.columns = new ArrayList<>();
        for(int i = 0; i < columnNames.length; i++){
            columns.add(new Column(columnNames[i], typeNames[i]));
        }
        this.numberOfColumns = columnNames.length;
    }

    /**
     * Copy constructor, does only a shallow copy of given columns.
     * @param columns Columns to make a copy of in a new DF object.
     */
    public DataFrame(Column[] columns){
        this.columns = new ArrayList<>(Arrays.asList(columns));
        this.numberOfColumns = columns.length;
    }

    /**
     * Add new row to the DataFrame. Parameters must be of correct types (according to column's type information).
     * @param objects List of objects to add. Throws exception if count doesn't match the columns count.
     */
    public void add(Object... objects){
        if(objects.length != numberOfColumns)
            throw new IllegalArgumentException("Wrong number of arguments");
        if(frozen)
            throw new IllegalStateException("Tried adding new data to a frozen DataFrame");

        for(int i = 0; i < objects.length; i++){
            columns.get(i).addData(objects[i]);
        }
    }

    /**
     * @return number of rows in the DataFrame. Returns -1 if there's no columns in the DataFrame.
     */
    public int size(){
        if(columns.size() > 0)
            return columns.get(1).getSize();
        else
            return -1;
    }

    /**
     * Returns one column from DataFrame. Helper function with copy parameter for two argument method set to true.
     * @param columnName Name of the column to retrieve.
     * @return Wanted copied column object.
     */
    public Column getColumn(String columnName){
        return this.getColumn(columnName, true);
    }

    /**
     * Returns one column from DataFrame. Can do deep or shallow copy of the columns.
     * @param columnName Names of columns to copy.
     * @param copy Whether to make of deep or shallow copy, true if deep.
     * @return Wanted copied column object.
     */
    public Column getColumn(String columnName, boolean copy){
        for(Column c : columns) {
            if( c.getName().equals(columnName) ){
                return copy ? new Column(c) : c;
            }
        }
        return null;
    }

    /**
     * Returns requested columns from DataFrame. Helper function with copy parameter for two argument method set to true
     * @param columnNames Names of columns to copy.
     * @return New DataFrame with copied requested columns.
     */
    public DataFrame getColumns(String[] columnNames){
        return this.getColumns(columnNames, true);
    }

    /**
     * Returns requested columns from DataFrame. Can do a deep or shallow copy of the columns.
     * @param columnNames Names of columns to copy.
     * @param copy Whether to make of deep or shallow copy, true if deep.
     * @return New DataFrame with copied requested columns.
     */
    public DataFrame getColumns(String[] columnNames, boolean copy){
        ArrayList<Column> resultColumns = new ArrayList<>();
        for(String columnName : columnNames){
            for(Column c : columns) {
                if( c.getName().equals(columnName) ){
                    if(copy)
                        resultColumns.add(new Column(c));
                    else
                        resultColumns.add(c);
                }
            }
        }
        if(resultColumns.size() != columnNames.length)
            throw new IllegalArgumentException("Could find all requested columns");
        Column[] newColsArr = resultColumns.toArray(new Column[resultColumns.size()]);
        DataFrame result = new DataFrame(newColsArr);
        if(!copy)
            result.setFrozen(true);
        return result;
    }

    /**
     * Helper function to copy only the Column structure without the data in them.
     * @return New DataFrame with copied structure.
     */
    public DataFrame copyDataFrameStructure(){
        String[] columnNames = new String[numberOfColumns];
        String[] typeNames = new String[numberOfColumns];
        for(int i = 0; i < numberOfColumns; i++){
            columnNames[i] = columns.get(i).getName();
            typeNames[i] = columns.get(i).getTypeName();
        }
        return new DataFrame(columnNames, typeNames);
    }

    /**
     * Get new DataFrame with only n-th row of the original one in it.
     * @param n number of row to copy.
     * @return New DataFrame with only one row copied.
     */
    public DataFrame getRow(int n) { // – zwracającą wiersz o podanym indeksie (jako nową DataFrame)
        DataFrame result = this.copyDataFrameStructure();
        Object[] toAdd = new Object[numberOfColumns];
        for(int i = 0; i < numberOfColumns; i++){
            toAdd[i] = columns.get(i).getData(n);
        }
        result.add(toAdd);
        return result;
    }

    /**
     * Get new DataFrame with only specified rows of the original one in it.
     * @param from Starting row to copy. (including, row 0).
     * @param to Ending row to copy (excluding).
     * @return New DataFrame with only specified rows copied.
     */
    public DataFrame getRows(int from, int to) {
        DataFrame result = this.copyDataFrameStructure();

        for (int j = from; j < to; j++) {
            Object[] toAdd = new Object[numberOfColumns];
            for(int i = 0; i < numberOfColumns; i++){
                toAdd[i] = columns.get(i).getData(j);
            }
            result.add(toAdd);
        }

        return result;
    }

    /**
     * 'frozen' field set to true means that this particular DataFrame is a shallow copy and modifying data in it will
     * result in a corrupted original (different sizes of columns for example).
     * @return Value of frozen field.
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Sets isFrozen field. Use if you know the consequences. By adding to a shallow copy of DataFrame you can corrupt
     * the original because it will have uneven number of rows in different columns which shouldn't be a case.
     * @param frozen value of frozen to set.
     */
    public void setFrozen(boolean frozen) {
        if(this.frozen && !frozen)
            System.out.println("You're unfreezing a frozen DataFrame, this may lead " +
                    "to messing up a DataFrame this one is a shallow copy of, be aware.");
        this.frozen = frozen;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfColumns; i++) {
            sb.append(columns.get(i).toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}
