import values.COOValue;
import values.Value;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SparseColumn extends Column {

    //From Column:
    // String name
    // Class<? extends Value> type

    /**
     * String representation of objects stored in this column. Used for comparison whether a newly added object is
     * different from the default (hidden) one and should be stored as an exception (COOValue) in the ArrayList or not.
     */
    private String hidden;
    /**
     * Number of rows in the column.
     */
    private int size;
    /**
     * List of exceptions in a form of COOValue objects (pairs of: int index - Value value).
     */
    private List<COOValue> data;

    /**
     * Default constructor.
     *
     * @param name   Name of the column.
     * @param type   Class of objects stored in this column.
     * @param hidden String representation of values to treat as the default value for this column.
     */
    public SparseColumn(String name, Class<? extends Value> type, String hidden) {
        super(name, type);
        this.data = new ArrayList<>();
        this.hidden = hidden;
    }

    /**
     * Deep copy constructor.
     *
     * @param column another SparseColumn to copy everything from.
     */
    public SparseColumn(SparseColumn column) {
        this.name = column.getName();
        this.type = column.getType();
        this.hidden = column.hidden;
        this.size = column.size;
        this.data = new ArrayList<>(column.data);
    }

    /**
     * Deep copy constructor converting Column to SparseColumn.
     *
     * @param column Column object to do a copy of.
     * @param hidden String representation of values to treat as the default value for this column.
     */
    //TODO: variant without the hidden parameter, instead infer it from column values.
    public SparseColumn(Column column, String hidden) {
        super(column);
        this.hidden = hidden;
    }

    /**
     * Adds new object. If equal to the 'hidden' field only 'size' is incremented, otherwise new
     * exception is added to the list.
     *
     * @param s String to create Value object from and to add to the column.
     */
    @Override
    public void addData(String s) {
        if (s.equals(hidden)) //if new object is has equal string repr. as the hidden value only inc the size.
            size++;
        else { //if not, add new exception and the increment size.
            try {
                data.add(new COOValue(size, type.getConstructor().newInstance().create(s)));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            size++;
        }
    }

    /**
     * Returns object from the n-th row, either from exception list or converted from default value.
     *
     * @param n number of row to retrieve the object from.
     * @return object from the n-th row.
     */
    @Override
    public Value getData(int n) {
        Value result = null;

        //check for data in the exception list.
        for (COOValue entry : data) {
            if (entry.getIndex() == n)
                result = entry.getValue();
        }

        // if not in the exception list - create new object from hidden.
        try {
            result = type.getConstructor().newInstance(hidden);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns number of rows in the column.
     *
     * @return number of rows in the column.
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Returns string representation of the default object in the column.
     *
     * @return string representation of the default object in the column.
     */
    public String getHidden() {
        return hidden;
    }

    /**
     * Returns string representations of this column.
     * Format: name (type) [default value]: content listed as an array.
     *
     * @return string representations of this column.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('[');
        for (int i = 0; i < this.size; i++) {
            boolean found = false;
            for (COOValue entry : this.data) {
                if (entry.getIndex() == i) {
                    result.append(entry.getValue().toString());
                    found = true;
                }
            }
            if (!found)
                result.append(this.hidden);
            if (i < size - 1)
                result.append(", ");
        }
        result.append(']');
        return String.format("%s (%s) [%s]: ", name, type, hidden) + result.toString();
    }
}
