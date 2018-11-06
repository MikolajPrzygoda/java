import values.COOValue;
import values.Value;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class SparseDataFrame extends DataFrame {

    private String hidden;
    private int size;

    public SparseDataFrame(String[] columnNames, Class<? extends Value> type, String hidden) {
        this.hidden = hidden;
        this.size = 0;
        for (String name : columnNames)
            addColumn(new Column(name, type));
    }

    public SparseDataFrame(DataFrame dataFrame, String hidden) {
        this.hidden = hidden;
        this.size = 0;
        if (dataFrame.width() > 0) {
            // Check if they are of the same type
            Class[] types = dataFrame.getColumnsTypes();
            Class type = types[0];
            for (Class c : types)
                if (!c.equals(type))
                    throw new UnsupportedOperationException("Can't convert a DataFrame with different types in it.");

            // Create columns
            columns = new ArrayList<>(dataFrame.width());
            String[] names = dataFrame.getColumnsNames();
            for (String name : names) {
                columns.add(new Column(name, type));
            }

            // Copy data row by row
            for (int i = 0; i < dataFrame.size(); i++)
                add(dataFrame.getValuesRow(i));
        }
    }

    /**
     * Copy constructor, does a deep copy of given columns.
     *
     * @param columns Columns to make a copy of in a new SparseDataFrame object.
     * @param hidden  String representation of the default value.
     */
    public SparseDataFrame(Column[] columns, String hidden) {
        this.hidden = hidden;
        this.size = 0;
        this.columns = new ArrayList<>(columns.length);

        for (Column column : columns)
            this.columns.add(new Column(column));
    }

    /**
     * Returns requested columns from SparseDataFrame.
     * Helper function with copy parameter for two argument method set to true
     *
     * @param columnNames Names of columns to copy.
     * @return New SparseDataFrame with copied requested columns.
     */
    @Override
    public SparseDataFrame getColumns(String[] columnNames) {
        return getColumns(columnNames, true);
    }

    /**
     * Returns requested columns from SparseDataFrame. Can do a deep or shallow copy of the columns.
     *
     * @param columnNames Names of columns to copy.
     * @param copy        Whether to make of deep or shallow copy, true if deep.
     * @return New SparseDataFrame with copied requested columns.
     * @throws IllegalArgumentException if not all of the requested columns were found.
     */
    @Override
    public SparseDataFrame getColumns(String[] columnNames, boolean copy) {
        return new SparseDataFrame(((DataFrame) this).getColumns(columnNames, copy), hidden);
    }

    /**
     * Helper function to copy only the column structure without the data in them.
     *
     * @return New SparseDataFrame with copied structure.
     */
    @Override
    protected SparseDataFrame copyStructure() {
        return new SparseDataFrame(getColumnsNames(), getColumnsTypes()[0], getHidden());
    }

    /**
     * Get new SparseDataFrame with only n-th row of the original one in it.
     *
     * @param n number of row to copy.
     * @return New SparseDataFrame with only one row copied.
     */
    @Override
    public SparseDataFrame getRow(int n) {
        return new SparseDataFrame(((DataFrame) this).getRow(n), hidden);
    }

    /**
     * Get new SparseDataFrame with only specified rows of the original one in it.
     *
     * @param from Starting row to copy. (including, row 0).
     * @param to   Ending row to copy (excluding).
     * @return New SparseDataFrame with only specified rows copied.
     */
    @Override
    public SparseDataFrame getRows(int from, int to) {
        return new SparseDataFrame(((DataFrame) this).getRows(from, to), hidden);
    }

    /**
     * Get n-th row of DataFrame as Value[].
     *
     * @param n number of row to return.
     * @return Array of values in the n-th row.
     * @throws IndexOutOfBoundsException if n is bigger or equal than current size of the column.
     */
    @Override
    public Value[] getValuesRow(int n) {
        if (n >= size())
            throw new IndexOutOfBoundsException("Requested index: " + n + ", current column size: " + size());
        Value[] result = new Value[width()];
        for (int i = 0; i < width(); i++) {
            COOValue exception = null;
            for (int j = 0; j < columns.get(i).size(); j++) {
                COOValue pair = (COOValue) columns.get(i).getData(j);
                if (n == pair.getIndex()) {
                    exception = pair;
                    break;
                }
            }
            if (exception == null) {
                result[i] = convertToType(hidden);
            } else
                result[i] = exception.getValue();
        }
        return result;
    }

    /**
     * Get n-th row of DataFrame as String[].
     *
     * @param n number of row to return.
     * @return Array of string representation of values in the n-th row.
     * @throws IndexOutOfBoundsException if n is bigger or equal than current size of the column.
     */
    @Override
    public String[] getStringRow(int n) {
        if (n >= size())
            throw new IndexOutOfBoundsException("Requested index: " + n + ", current column size: " + size());
        String[] result = new String[width()];
        for (int i = 0; i < width(); i++) {
            COOValue exception = null;
            for (int j = 0; j < columns.get(i).size(); j++) {
                COOValue pair = (COOValue) columns.get(i).getData(j);
                if (n == pair.getIndex()) {
                    exception = pair;
                    break;
                }
            }
            if (exception == null) {
                result[i] = hidden;
            } else
                result[i] = exception.getValue().toString();
        }
        return result;
    }

    /**
     * Add new row to the SparseDataFrame.
     *
     * @param objects List of objects to add. Throws exception if count doesn't match the columns count.
     * @throws IllegalArgumentException if number of arguments doesn't match DataFrame width.
     * @throws IllegalStateException    if the DataFrame is isReadOnly when adding new row.
     * @throws IllegalStateException    if the DataFrame doesn't have any columns.
     */
    @Override
    public void add(String... objects) {
        if (objects.length != this.width())
            throw new IllegalArgumentException("Wrong number of arguments");
        if (width() == 0)
            throw new IllegalStateException("Tried adding new data to a DataFrame with no columns");
        if (isReadOnly)
            throw new IllegalStateException("Tried adding new data to a isReadOnly DataFrame");

        for (int i = 0; i < objects.length; i++) {
            if (!objects[i].equals(hidden)) {
                COOValue exception = new COOValue(size, convertToType(objects[i]));
                columns.get(i).addData(exception);
            }
            size++;
        }
    }

    /**
     * Add new row to the SparseDataFrame.
     *
     * @param values List of objects to add. Throws exception if count doesn't match the columns count.
     * @throws IllegalArgumentException if number of arguments doesn't match DataFrame width.
     * @throws IllegalStateException    if the DataFrame is isReadOnly when adding new row.
     * @throws IllegalStateException    if the DataFrame doesn't have any columns.
     */
    @Override
    public void add(Value... values) {
        if (values.length != this.width())
            throw new IllegalArgumentException("Wrong number of arguments");
        if (width() == 0)
            throw new IllegalStateException("Tried adding new data to a DataFrame with no columns");
        if (isReadOnly)
            throw new IllegalStateException("Tried adding new data to a isReadOnly DataFrame");

        for (int i = 0; i < values.length; i++) {
            if (!values[i].toString().equals(hidden)) {
                COOValue exception = new COOValue(size, values[i]);
                columns.get(i).addData(exception);
            }
            size++;
        }
    }

    private Value convertToType(String s) {
        if (width() > 0) {
            try {
                return columns.get(0).getType().getConstructor(String.class).newInstance(s);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Tried converting a value when there's no columns in data frame, returned null.");
        return null;
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

    public String getHidden() {
        return hidden;
    }

}
