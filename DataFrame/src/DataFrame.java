import values.Value;

import java.util.ArrayList;
import java.util.List;


public class DataFrame {

    /**
     * Set to true when there's made a shallow copy of DataFrame without all the original columns and then blocks anyone
     * from from adding new data in order to prevent the corruption of original DataFrame that has all the columns.
     */
    protected boolean isReadOnly = false;
    protected List<Column> columns;

    /**
     * Default constructor creating empty DataFrame.
     */
    protected DataFrame() {
        this.columns = new ArrayList<>();
    }

    /**
     * Given arrays must be of equal lengths.
     *
     * @param columnNames Names of columns to create.
     * @param types       Classes of objects to store in columns.
     * @throws IllegalArgumentException if columnNames and typeNames lengths aren't equal.
     */
    public DataFrame(String[] columnNames, Class<? extends Value>[] types) {
        if (columnNames.length != types.length)
            throw new IllegalArgumentException("Name and Class arrays lengths don't match up");

        this.columns = new ArrayList<>();
        for (int i = 0; i < columnNames.length; i++) {
            this.columns.add(new Column(columnNames[i], types[i]));
        }
    }

    /**
     * Copy constructor, does a deep copy of given columns.
     *
     * @param columns Columns to make a copy of in a new DataFrame object.
     */
    public DataFrame(Column[] columns) {
        this.columns = new ArrayList<>(columns.length);
        for (Column column : columns)
            this.columns.add(new Column(column));
    }

    /**
     * Constructs SpraseDataFrame from a regular DataFrame. Does a deep copy.
     *
     * @param sparseDataFrame SparseDataFrame to convert.
     */
    public DataFrame(SparseDataFrame sparseDataFrame) {
        //copy structure
        String[] names = sparseDataFrame.getColumnsNames();
        Class[] types = sparseDataFrame.getColumnsTypes();
        this.columns = new ArrayList<>(names.length);
        for (int i = 0; i < names.length; i++) {
            columns.add(new Column(names[i], types[i]));
        }

        //copy data
        for (int i = 0; i < sparseDataFrame.size(); i++) {
            this.add(sparseDataFrame.getValuesRow(i));
        }
    }

    /**
     * Constructs DataFrame and reads data from file. First line consists of names of the columns to create.
     *
     * @param fileName file to read.
     * @param types    types of columns.
     */
    public DataFrame(String fileName, Class<? extends Value>[] types) {

    }

    /**
     * Constructs DataFrame and reads data from file.
     *
     * @param fileName    file to read.
     * @param types       types of columns.
     * @param columnNames names of columns.
     */
    public DataFrame(String fileName, Class<? extends Value>[] types, String[] columnNames) {

    }

    /**
     * Add a new Column to the DataFrame, only if there's no rows in the DataFrame.
     *
     * @param column Column to be added to the DataFrame.
     * @throws IllegalStateException if there's anything in the DataFrame or the column.
     */
    public void addColumn(Column column) {
        if (this.size() > 0 || column.size() > 0) //must be 0 or -1
            throw new IllegalStateException("Can't add new columns when there's already any data in the DataFrame");
        columns.add(column);
    }

    /**
     * Add new row to the DataFrame.
     *
     * @param values List of values to add.
     * @throws IllegalArgumentException if number of arguments doesn't match DataFrame width.
     * @throws IllegalStateException    if the DataFrame is isReadOnly when adding new row.
     */
    public void add(Value... values) {
        if (values.length != this.width())
            throw new IllegalArgumentException("Wrong number of arguments");
        if (isReadOnly)
            throw new IllegalStateException("Tried adding new data to a isReadOnly DataFrame");

        for (int i = 0; i < values.length; i++) {
            columns.get(i).addData(values[i]);
        }
    }

    /**
     * Add new row to the DataFrame. Parameters will be converted from String to their corresponding types.
     *
     * @param values List of String representations of objects to add.
     * @throws IllegalArgumentException if number of arguments doesn't match DataFrame width.
     * @throws IllegalStateException    if the DataFrame is isReadOnly when adding new row.
     */
    public void add(String... values) {
        if (values.length != this.width())
            throw new IllegalArgumentException("Wrong number of arguments");
        if (isReadOnly)
            throw new IllegalStateException("Tried adding new data to a isReadOnly DataFrame");

        for (int i = 0; i < values.length; i++) {
            columns.get(i).addData(values[i]);
        }
    }

    /**
     * Returns number of rows in the DataFrame or -1 if there's no columns in the DataFrame.
     *
     * @return number of rows in the DataFrame.
     */
    public int size() {
        if (this.width() > 0)
            return columns.get(0).size();
        else
            return -1;
    }

    /**
     * Returns number of columns in the DataFrame.
     *
     * @return number of columns in the DataFrame.
     */
    public int width() {
        return columns.size();
    }

    /**
     * Returns one column from DataFrame. Helper function with copy parameter for two argument method set to true.
     *
     * @param columnName Name of the column to retrieve.
     * @return Wanted deep copied column object.
     */
    public Column getColumn(String columnName) {
        return this.getColumn(columnName, true);
    }

    /**
     * Returns one column from DataFrame. Can do deep or shallow copy of the columns.
     *
     * @param columnName Names of columns to copy.
     * @param copy       Whether to make of deep or shallow copy, true for a deep copy.
     * @return Wanted copied column object.
     */
    public Column getColumn(String columnName, boolean copy) {
        for (Column c : columns) {
            if (c.getName().equals(columnName)) {
                return copy ? new Column(c) : c;
            }
        }
        return null;
    }

    /**
     * Returns requested columns from DataFrame. Helper function with copy parameter for two argument method set to true
     *
     * @param columnNames Names of columns to copy.
     * @return New DataFrame with deep copied requested columns.
     */
    public DataFrame getColumns(String[] columnNames) {
        return this.getColumns(columnNames, true);
    }

    /**
     * Returns requested columns from DataFrame. Can do a deep or shallow copy of the columns.
     *
     * @param columnNames Names of columns to copy.
     * @param copy        Whether to make of deep or shallow copy, true for a deep copy.
     * @return New DataFrame with copied requested columns.
     * @throws IllegalArgumentException if not all of the requested columns were found.
     */
    public DataFrame getColumns(String[] columnNames, boolean copy) {
        ArrayList<Column> resultColumns = new ArrayList<>();
        for (String columnName : columnNames) {
            for (Column c : columns) {
                if (c.getName().equals(columnName)) {
                    if (copy)
                        resultColumns.add(new Column(c));
                    else
                        resultColumns.add(c);
                }
            }
        }

        if (resultColumns.size() != columnNames.length)
            throw new IllegalArgumentException("Couldn't find all of the requested columns");

        Column[] newColsArr = resultColumns.toArray(new Column[0]);
        DataFrame result = new DataFrame(newColsArr);
        if (!copy || columns.size() != columnNames.length)
            result.setReadOnly(true);
        return result;
    }

    /**
     * Helper function to copy only the Column structure without the data in them.
     *
     * @return New DataFrame with copied structure.
     */
    protected DataFrame copyStructure() {
        return new DataFrame(this.getColumnsNames(), this.getColumnsTypes());
    }

    /**
     * Get n-th row of DataFrame as Value[].
     *
     * @param n number of row to return.
     * @return Array of values in the n-th row.
     * @throws IndexOutOfBoundsException if n is bigger or equal than current size of the column.
     */
    public Value[] getValuesRow(int n) {
        if (n >= size())
            throw new IndexOutOfBoundsException("Requested index: " + n + ", current columns size: " + size());
        Value[] result = new Value[width()];
        for (int i = 0; i < width(); i++) {
            result[i] = columns.get(i).getData(n);
        }
        return result;
    }

    /**
     * Get new DataFrame with only n-th row of the original one in it.
     *
     * @param n number of row to copy.
     * @return New DataFrame with only one row copied.
     */
    public DataFrame getRow(int n) {
        DataFrame result = this.copyStructure();
        Value[] toAdd = new Value[width()];
        for (int i = 0; i < width(); i++) {
            toAdd[i] = columns.get(i).getData(n);
        }
        result.add(toAdd);
        return result;
    }

    /**
     * Get new DataFrame with only specified rows of the original one in it.
     *
     * @param from Starting row to copy. (including, row 0).
     * @param to   Ending row to copy (excluding).
     * @return New DataFrame with only specified rows copied.
     */
    public DataFrame getRows(int from, int to) {
        DataFrame result = this.copyStructure();
        Value[] toAdd = new Value[width()];
        for (int j = from; j < to; j++) {
            for (int i = 0; i < width(); i++) {
                toAdd[i] = columns.get(i).getData(j);
            }
            result.add(toAdd);
        }
        return result;
    }

    /**
     * 'isReadOnly' field set to true means that this particular DataFrame is a shallow copy and modifying data
     * in it will result in original in corrupted state (different sizes of columns for example).
     *
     * @return value of isReadOnly field.
     */
    public boolean isReadOnly() {
        return isReadOnly;
    }

    /**
     * Sets isReadOnly field. Use if you know the consequences. By modifying a shallow copy of DataFrame you can corrupt
     * the original because it will have uneven number of rows in different columns what is not allowed.
     *
     * @param readOnly value of isReadOnly to set.
     */
    public void setReadOnly(boolean readOnly) {
        if (this.isReadOnly && !readOnly)
            System.out.println("You're removing a 'Read Only' flag from a DataFrame, this may lead " +
                                       "to messing up a DataFrame this one is a shallow copy of, be aware.");
        this.isReadOnly = readOnly;
    }

    /**
     * Returns array with column names.
     *
     * @return array with column names.
     */
    public String[] getColumnsNames() {
        String[] result = new String[width()];
        for (int i = 0; i < width(); i++) {
            result[i] = columns.get(i).getName();
        }
        return result;
    }

    /**
     * Returns array with column typeNames.
     *
     * @return array with column typeNames.
     */
    public Class[] getColumnsTypes() {
        Class[] result = new Class[width()];
        for (int i = 0; i < width(); i++) {
            result[i] = columns.get(i).getType();
        }
        return result;
    }

    /**
     * TODO
     *
     * @param columnNames
     * @return
     */
    public DataFrameGroup groupBy(String[] columnNames) {
        return new DataFrameGroup(this, columnNames);
    }

    /**
     * Returns string representation of DataFrame column by column, each in new row.
     *
     * @return string representation of DataFrame.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Column column : columns) {
            sb.append(column.toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}
