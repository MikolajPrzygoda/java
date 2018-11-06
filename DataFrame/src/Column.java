import values.Value;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Column{

    private String name;
    private Class<? extends Value> type;
    private ArrayList<Value> data;

    public Column(String name, Class<? extends Value> type){
        this.name = name;
        this.type = type;
        this.data = new ArrayList<>();
    }

    public Column(Column column){
        this.name = column.name;
        this.type = column.type;

        this.data = new ArrayList<>(column.data.size());
        for (Value v : column.data) {
            this.data.add(v.clone());
        }
    }

    public String getName(){
        return name;
    }

    public Class<? extends Value> getType(){
        return type;
    }

    public void addData(Value value) {
        if (type.isInstance(value))
            data.add(value);
    }
    public void addData(String s){
        try {
            Value v = type.getConstructor(String.class).newInstance(s);
            data.add(v);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public Value getData(int n){
        return data.get(n);
    }

    public int size() {
        return data.size();
    }

    @Override
    public String toString(){
        return String.format("%s (%s): ", name, type) + data.toString();
    }
}
