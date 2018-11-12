import values.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        else if (value == null) {
            throw new IllegalStateException("Tried adding null to the column.");
        } else {
            throw new IllegalArgumentException(
                    "To column: " + name + " with type: " + type.toString() +
                            " tried adding value of type: " + value.getClass().toString());
        }
    }
    public void addData(String s){
        try {
            data.add(type.getConstructor(String.class).newInstance(s));
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

    public Column add(Value value) {
        Column result = new Column(name, type);
        for (Value d : data) {
            result.addData(d.add(value));
        }
        return result;
    }

    public Column add(Column other) {
        Column result = new Column(name, type);
        for (int i = 0; i < data.size(); i++) {
            result.addData(data.get(i).add(other.getData(i)));
        }
        return result;
    }

    public Column sub(Value value) {
        Column result = new Column(name, type);
        for (Value d : data) {
            result.addData(d.sub(value));
        }
        return result;
    }

    public Column sub(Column other) {
        Column result = new Column(name, type);
        for (int i = 0; i < data.size(); i++) {
            result.addData(data.get(i).sub(other.getData(i)));
        }
        return result;
    }

    public Column mul(Value value) {
        Column result = new Column(name, type);
        for (Value d : data) {
            result.addData(d.mul(value));
        }
        return result;
    }

    public Column mul(Column other) {
        Column result = new Column(name, type);
        for (int i = 0; i < data.size(); i++) {
            result.addData(data.get(i).mul(other.getData(i)));
        }
        return result;
    }

    public Column div(Value value) {
        Column result = new Column(name, type);
        for (Value d : data) {
            result.addData(d.div(value));
        }
        return result;
    }

    public Column div(Column other) {
        Column result = new Column(name, type);
        for (int i = 0; i < data.size(); i++) {
            result.addData(data.get(i).div(other.getData(i)));
        }
        return result;
    }

    public Column pow(Value value) {
        Column result = new Column(name, type);
        for (Value d : data) {
            result.addData(d.pow(value));
        }
        return result;
    }

    public Column pow(Column other) {
        Column result = new Column(name, type);
        for (int i = 0; i < data.size(); i++) {
            result.addData(data.get(i).pow(other.getData(i)));
        }
        return result;
    }

    @Override
    public String toString(){
        return String.format("%s (%s): ", name, type) + data.toString();
    }
}
