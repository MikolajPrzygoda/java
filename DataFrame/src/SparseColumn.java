import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SparseColumn extends Column{

    /**
     * String representation of objects stored in this column. Used for comparison whether a newly added object is
     * different from the default (hidden) one and should be stored as an exception (C00Value) in the ArrayList or not.
     */
    private String hidden;
    /**
     * Number of rows in the column.
     */
    private int size;
    /**
     * List of exceptions in a form of C00Value objects (pairs of: int index - Object value).
     */
    private List<C00Value> data;
    //TODO: isSorted field, sort method and logarithmic search in getData(int) if column is sorted.

    /**
     * Default constructor. In order to store users class objects it must have .valueOf(String) and .toString() methods.
     * @param name     Name of the column.
     * @param typeName Class name (full, with package) of objects stored in this column.
     * @param hidden   String representation of values to treat as the default value for this column.
     */
    public SparseColumn(String name, String typeName, String hidden){
        super(name, typeName);
        this.data = new ArrayList<>();
        this.hidden = hidden;
    }

    /**
     * Deep copy constructor.
     * @param column another SparseColumn to copy everything from.
     */
    public SparseColumn(SparseColumn column){
        this.name = column.getName();
        this.typeName = column.getTypeName();
        this.data = new ArrayList<>(column.data);
        this.hidden = column.hidden;
    }

    /**
     * Deep copy constructor converting Column to SparseColumn.
     * @param column Column object to do a copy of.
     * @param hidden String representation of values to treat as the default value for this column.
     */
    //TODO: variant without the hidden parameter, instead infer it from column values.
    public SparseColumn(Column column, String hidden){
        super(column);
        this.hidden = hidden;
    }

    /**
     * Used for converting String from 'hidden' field to the correct object of a class stored in this column.
     * Uses .valueOf(String) method of class specified in typeName field (obtained via the .getType() method).
     * @param s String to convert.
     * @return Correct Object converted from string.
     * @throws UnsupportedOperationException if class of this column doesn't have .valueOf(String) method.
     */
    private Object convert(String s){
        Class clazz = getType();
        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            if(method.getName().equals("valueOf")){
                if(method.getGenericParameterTypes().length == 1){
                    if(method.getGenericParameterTypes()[0].getTypeName().equals("java.lang.String")){
                        try{
                            return method.invoke(clazz, s);
                        } catch(IllegalAccessException | InvocationTargetException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Adds new object. If its .toString() is equal to the 'hidden' field only 'size' is incremented, otherwise new
     * exception is added to the list.
     * @param o object to add to the column.
     * @throws IllegalArgumentException if object is not of the type stored in this column.
     */
    @Override
    public void addData(Object o){
        if(!validate(o))
            throw new IllegalArgumentException("Given argument doesn't match the specified column type");
        if(o.toString().equals(hidden)) //if new object is has equal string repr. as the hidden value only inc the size.
            size++;
        else{ //if not, add new exception and the increment size.
            data.add(new C00Value(size, o));
            size++;
        }
    }

    /**
     * Returns object from the n-th row, either from exception list or converted from default value.
     * @param n number of row to retrieve the object from.
     * @return object from the n-th row.
     */
    @Override
    public Object getData(int n){
        for(C00Value entry : data){
            if(entry.getIndex() == n)
                return entry.getValue();
        }
        return convert(hidden);
    }

    /**
     * Returns number of rows in the column.
     * @return number of rows in the column.
     */
    @Override
    public int getSize(){
        return size;
    }

    /**
     * Returns string representation of the default object in the column.
     * @return string representation of the default object in the column.
     */
    public String getHidden(){
        return hidden;
    }

    /**
     * Returns string representations of this column.
     * Format: name (typeName) [default value]: content listed as an array.
     * @return string representations of this column.
     */
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append('[');
        for(int i = 0; i < this.size; i++){
            boolean found = false;
            for(C00Value entry : this.data){
                if(entry.getIndex() == i){
                    result.append(entry.getValue().toString());
                    found = true;
                }
            }
            if(!found)
                result.append(this.hidden);
            if(i < size - 1)
                result.append(", ");
        }
        result.append(']');
        return String.format("%s (%s) [%s]: ", name, typeName, hidden) + result.toString();
    }
}
