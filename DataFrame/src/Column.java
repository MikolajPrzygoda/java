import com.sun.org.apache.bcel.internal.generic.FLOAD;

import java.util.ArrayList;

public class Column {
    private String name;
    private String typeName;
    private ArrayList<Object> data;

    public Column(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;
        this.data = new ArrayList<>();
    }

    public Column(Column column){
        this.name = column.name;
        this.typeName = column.typeName;
        this.data = new ArrayList<>(column.data);
    }

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void addData(Object o){
        if(validate(o))
            data.add(o);
        else
            throw new IllegalArgumentException("Given argument doesn't match the specified column type");
    }

    public Object getData(int n){
        return data.get(n);
    }

    public int getSize(){
        return data.size();
    }

    private boolean validate(Object o) {
        switch (typeName){
            case "byte":
            case "Byte":
                return o instanceof Byte;
            case "short":
            case "Short":
                return o instanceof Short;
            case "int":
            case "Integer":
                return o instanceof Integer;
            case "long":
            case "Long":
                return o instanceof Long;
            case "float":
            case "Float":
                return o instanceof Float;
            case "double":
            case "Double":
                return o instanceof Double;
            case "boolean":
            case "Boolean":
                return o instanceof Boolean;
            case "char":
            case "Character":
                return o instanceof Character;
            case "String":
                return o instanceof String;
            default:
                try {
                    return Class.forName(typeName).isInstance(o);
                } catch (ClassNotFoundException e) {
                    return false;
                }
        }
    }

    @Override
    public String toString() {
        return String.format("%s (%s): ", name, typeName) + data.toString();
    }
}
