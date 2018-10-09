import java.util.ArrayList;
import java.util.Arrays;

public class DataFrame {

    private ArrayList<Column> columns;
    private int numberOfColumns;

    //Set to true when there's made a shallow copy of DataFrame without all the original columns and then blocks anyone
    //from from adding new data in order to prevent the corruption of original DataFrame all the columns is in.
    private boolean frozen = false;

    DataFrame(String[] columnNames, String[] typeNames) {
        if(columnNames.length != typeNames.length)
            throw new IllegalArgumentException("Name and Class arrays lengths don't match up");

        this.columns = new ArrayList<>();
        for(int i = 0; i < columnNames.length; i++){
            columns.add(new Column(columnNames[i], typeNames[i]));
        }
        this.numberOfColumns = columnNames.length;
    }

    DataFrame(Column[] columns){
        this.columns = new ArrayList<>(Arrays.asList(columns));
        this.numberOfColumns = columns.length;
    }

    public void add(Object... objects){
        if(objects.length != numberOfColumns)
            throw new IllegalArgumentException("Wrong number of arguments");
        if(frozen)
            throw new IllegalStateException("Tried adding new data to a frozen DataFrame");

        for(int i = 0; i < objects.length; i++){
            columns.get(i).addData(objects[i]);
        }
    }

    public int size(){
        if(columns.size() > 0)
            return columns.get(1).getSize();
        else
            return -1;
    }

    public Column getColumn(String columnName){
        return this.getColumn(columnName, true);
    }
    public Column getColumn(String columnName, boolean copy){
        for(Column c : columns) {
            if( c.getName().equals(columnName) ){
                if(copy)
                    return new Column(c);
                else
                    return c;
            }
        }
        return null;
    }

    public DataFrame getColumns(String[] columnNames){
        return this.getColumns(columnNames, true);
    }
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

    public DataFrame copyDataFrameStructure(){
        String[] columnNames = new String[numberOfColumns];
        String[] typeNames = new String[numberOfColumns];
        for(int i = 0; i < numberOfColumns; i++){
            columnNames[i] = columns.get(i).getName();
            typeNames[i] = columns.get(i).getTypeName();
        }
        return new DataFrame(columnNames, typeNames);
    }

    public DataFrame getRow(int n) { // – zwracającą wiersz o podanym indeksie (jako nową DataFrame)
        DataFrame result = this.copyDataFrameStructure();
        Object[] toAdd = new Object[numberOfColumns];
        for(int i = 0; i < numberOfColumns; i++){
            toAdd[i] = columns.get(i).getData(n);
        }
        result.add(toAdd);
        return result;
    }

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

    public boolean isFrozen() {
        return frozen;
    }

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
