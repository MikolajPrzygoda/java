import values.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SparseDataFrame extends DataFrame{

    private List<SparseColumn> columns;

    /**
     * Default constructor creating empty SparseDataFrame.
     */
    public SparseDataFrame(){
        this.columns = new ArrayList<>();
    }

    public SparseDataFrame(String[] columnNames, Class<? extends Value> type, String hidden){
        columns = new ArrayList<>(columnNames.length);
        for(String columnName : columnNames){
            columns.add(new SparseColumn(columnName, type, hidden));
        }
    }

    public SparseDataFrame(DataFrame dataFrame, String hidden){
        if(dataFrame.width() > 0){
            // Check if they are of the same type
            Class[] typeNames = dataFrame.getColumnsTypes();
            Class typeName = typeNames[0];
            for(Class c : typeNames)
                if(!c.equals(typeName))
                    throw new UnsupportedOperationException("Can't convert a DataFrame with different typeNames in it.");

            // Create columns
            columns = new ArrayList<>();
            String[] names = dataFrame.getColumnsNames();
            for(String name : names){
                columns.add(new SparseColumn(name, typeName, hidden));
            }

            // Copy data row by row
            for(int i = 0; i < dataFrame.size(); i++)
                add(dataFrame.getRowData(i));
        }
    }

    /**
     * Constructs a shallow copy
     * @param columns SparseColumns to make a copy of in a new SparseDataFrame object.
     */
    public SparseDataFrame(SparseColumn[] columns){
        this.columns = new ArrayList<>(Arrays.asList(columns));
    }

    /**
     * Returns number of rows in the SparseDataFrame or -1 if there's no columns in the SparseDataFrame.
     * @return number of rows in the SparseDataFrame.
     */
    @Override
    public int size(){
        if(width() > 0)
            return columns.get(0).getSize();
        else
            return -1;
    }

    /**
     * Returns number of columns in the SparseDataFrame.
     * @return number of columns in the SparseDataFrame.
     */
    @Override
    public int width(){
        return columns.size();
    }

    /**
     * Add a new SparseColumn to the SparseDataFrame, only if there no rows in the SparseDataFrame.
     * @param column SparseColumn to be added to the SparseDataFrame.
     * @throws IllegalStateException if there's any data in the SparseDataFrame when adding new SparseColumn.
     */
    public void addColumn(SparseColumn column){
        if(this.size() <= 0) //must be 0 or -1
            columns.add(column);
        else
            throw new IllegalStateException("Can't add new columns when there's already any data in the DataFrame");
    }

    //TODO: fix shallow/deep copy
    /**
     * Returns one column from DataFrame. Helper function with copy parameter for two argument method set to true.
     * @param columnName Name of the column to retrieve.
     * @return Wanted copied column object.
     */
    @Override
    public SparseColumn getColumn(String columnName){
        return getColumn(columnName, true);
    }

    /**
     * Returns one column from SparseDataFrame. Can do deep or shallow copy of the columns.
     * @param columnName Names of columns to copy.
     * @param copy       Whether to make of deep or shallow copy, true if deep.
     * @return Wanted copied column object.
     */
    @Override
    public SparseColumn getColumn(String columnName, boolean copy){
        for(SparseColumn column : columns){
            if(column.getName().equals(columnName)){
                return copy ? new SparseColumn(column) : column;
            }
        }
        return null;
    }

    /**
     * Returns requested columns from SparseDataFrame.
     * Helper function with copy parameter for two argument method set to true
     * @param columnNames Names of columns to copy.
     * @return New SparseDataFrame with copied requested columns.
     */
    @Override
    public SparseDataFrame getColumns(String[] columnNames){
        return getColumns(columnNames, true);
    }

    /**
     * Returns requested columns from SparseDataFrame. Can do a deep or shallow copy of the columns.
     * @param columnNames Names of columns to copy.
     * @param copy        Whether to make of deep or shallow copy, true if deep.
     * @return New SparseDataFrame with copied requested columns.
     * @throws IllegalArgumentException if not all of the requested columns were found.
     */
    @Override
    public SparseDataFrame getColumns(String[] columnNames, boolean copy){
        ArrayList<SparseColumn> resultColumns = new ArrayList<>();
        for(String columnName : columnNames){
            for(SparseColumn column : columns){
                if(column.getName().equals(columnName)){
                    if(copy)
                        resultColumns.add(new SparseColumn(column));
                    else
                        resultColumns.add(column);
                }
            }
        }

        if(resultColumns.size() != columnNames.length)
            throw new IllegalArgumentException("Couldn't find all requested columns");

        SparseColumn[] newColsArr = resultColumns.toArray(new SparseColumn[0]);
        SparseDataFrame result = new SparseDataFrame(newColsArr);
        if(!copy)
            result.setFrozen(true);
        return result;
    }

    /**
     * Helper function to copy only the column structure without the data in them.
     * @return New SparseDataFrame with copied structure.
     */
    @Override
    protected SparseDataFrame copyStructure(){
        return new SparseDataFrame(getColumnsNames(), getColumnsType(), getHidden());
    }

    /**
     * Get new SparseDataFrame with only n-th row of the original one in it.
     * @param n number of row to copy.
     * @return New SparseDataFrame with only one row copied.
     */
    @Override
    public SparseDataFrame getRow(int n){
        SparseDataFrame result = copyStructure();
        String[] toAdd = new String[width()];
        for(int i = 0; i < width(); i++){
            toAdd[i] = columns.get(i).getData(n).toString();
        }
        result.add(toAdd);
        return result;
    }

    /**
     * Get new SparseDataFrame with only specified rows of the original one in it.
     * @param from Starting row to copy. (including, row 0).
     * @param to   Ending row to copy (excluding).
     * @return New SparseDataFrame with only specified rows copied.
     */
    @Override
    public SparseDataFrame getRows(int from, int to){
        SparseDataFrame result = this.copyStructure();
        String[] toAdd = new String[width()];
        for(int j = from; j < to; j++){
            for(int i = 0; i < width(); i++){
                toAdd[i] = columns.get(i).getData(j).toString();
            }
            result.add(toAdd);
        }
        return result;
    }

    /**
     * Add new row to the DataFrame.
     * @param objects List of objects to add. Throws exception if count doesn't match the columns count.
     * @throws IllegalArgumentException if number of arguments doesn't match DataFrame width.
     * @throws IllegalStateException    if the DataFrame is frozen when adding new row.
     */
    @Override
    public void add(String... objects){
        if(objects.length != this.width())
            throw new IllegalArgumentException("Wrong number of arguments");
        if(frozen)
            throw new IllegalStateException("Tried adding new data to a frozen DataFrame");

        for(int i = 0; i < objects.length; i++){
            columns.get(i).addData(objects[i]);
        }
    }

    /**
     * Get n-th row of DataFrame as Object[].
     * @param n number of row to return.
     * @return Array of Objects from n-th row.
     * @throws IllegalArgumentException if n is bigger than current size of the column.
     */
    @Override
    public String[] getRowData(int n){
        if(n >= size())
            throw new IllegalArgumentException("Index of wanted row bigger than current size of the column.");
        String[] result = new String[width()];
        for(int i = 0; i < width(); i++){
            result[i] = columns.get(i).getData(n).toString();
        }
        return result;
    }

    /**
     * 'frozen' field set to true means that this particular DataFrame is a shallow copy and modifying data in it will
     * result in a corrupted original (different sizes of columns for example).
     * @return values.values of frozen field.
     */
    @Override
    public boolean isFrozen(){
        return frozen;
    }

    /**
     * Sets isFrozen field. Use if you know the consequences. By modifying a shallow copy of DataFrame you can corrupt
     * the original because it will have uneven number of rows in different columns which shouldn't be a case.
     * @param frozen value of frozen to set.
     */
    @Override
    public void setFrozen(boolean frozen){
        if(this.frozen && !frozen)
            System.out.println("You're unfreezing a frozen DataFrame, this may lead " +
                    "to messing up a DataFrame this one is a shallow copy of, be aware.");
        this.frozen = frozen;
    }

    /**
     * Returns array with column names.
     * @return array with column names.
     */
    @Override
    public String[] getColumnsNames(){
        String[] result = new String[width()];
        for(int i = 0; i < width(); i++){
            result[i] = columns.get(i).getName();
        }
        return result;
    }

    /**
     * Returns array with column typeNames.
     * @return array with column typeNames.
     */
    @Override
    public Class[] getColumnsTypes(){
        Class[] result = new Class[width()];
        for(int i = 0; i < width(); i++){
            result[i] = columns.get(i).getType();
        }
        return result;
    }

    /**
     * Returns string representation of DataFrame column by column, each in new row.
     * @return string representation of DataFrame.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Column column : columns){
            sb.append(column.toString());
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Returns columns type. Empty string if there's no column.
     * @return columns type.
     */
    public Class<? extends Value> getColumnsType(){
        if(size() > 0)
            return columns.get(0).getType();
        else
            return null;
    }


    public String getHidden(){
        if(size() > 0)
            return columns.get(0).getHidden();
        else
            return null;
    }

}
