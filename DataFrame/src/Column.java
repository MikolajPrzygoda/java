import java.util.ArrayList;

public class Column{

    protected String name;
    protected String typeName;
    private ArrayList<Object> data;

    public Column(){
    }

    public Column(String name, String typeName){
        this.name = name;
        this.typeName = typeName;
        this.data = new ArrayList<>();
    }

    public Column(Column column){
        this.name = column.name;
        this.typeName = column.typeName;
        this.data = new ArrayList<>(column.data);
    }

    public String getName(){
        return name;
    }

    public String getTypeName(){
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

    protected Class getType(){
        switch(typeName){
            case "byte":
            case "Byte":
                return Byte.class;
            case "short":
            case "Short":
                return Short.class;
            case "int":
            case "Integer":
                return Integer.class;
            case "long":
            case "Long":
                return Long.class;
            case "float":
            case "Float":
                return Float.class;
            case "double":
            case "Double":
                return Double.class;
            case "boolean":
            case "Boolean":
                return Boolean.class;
            case "char":
            case "Character":
                return Character.class;
            case "String":
                try{
                    return Class.forName("java.lang.String");
                } catch(ClassNotFoundException e){
                    return null;
                }
            default:
                try{
                    return Class.forName(typeName);
                } catch(ClassNotFoundException e){
                    return null;
                }
        }
    }

    protected boolean validate(Object o){
        return getType().isInstance(o);
    }

    @Override
    public String toString(){
        return String.format("%s (%s): ", name, typeName) + data.toString();
    }
}
