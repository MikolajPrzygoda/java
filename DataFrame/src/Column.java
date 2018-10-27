import values.Value;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Column{

    protected String name;
    protected Class<? extends Value> type;
    private ArrayList<Value> data;

    public Column(){
    }

    public Column(String name, Class<? extends Value> type){
        this.name = name;
        this.type = type;
        this.data = new ArrayList<>();
    }

    public Column(Column column){
        this.name = column.name;
        this.type = column.type;
        this.data = new ArrayList<>(column.data);
    }

    public String getName(){
        return name;
    }

    public Class<? extends Value> getType(){
        return type;
    }

    public void addData(String s){
        try {
            Value v = type.getConstructor(java.lang.String.class).newInstance(s);
            data.add(v);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public Value getData(int n){
        return data.get(n);
    }

    public int getSize(){
        return data.size();
    }

    @Override
    public String toString(){
        return String.format("%s (%s): ", name, type) + data.toString();
    }
}
