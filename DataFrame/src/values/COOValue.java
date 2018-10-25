package values;

public class COOValue{

    private int index;
    private Value value;

    public COOValue(int index, Value value){
        this.index = index;
        this.value = value;
    }

    public int getIndex(){
        return index;
    }

    public Value getValue(){
        return value;
    }

    @Override
    public java.lang.String toString(){
        return java.lang.String.format("[%d] - %s", index, value);
    }
}
