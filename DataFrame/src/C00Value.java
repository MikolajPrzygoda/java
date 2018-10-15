public class C00Value{

    private int index;
    private Object value;

    public C00Value(int index, Object value){
        this.index = index;
        this.value = value;
    }

    public int getIndex(){
        return index;
    }

    public Object getValue(){
        return value;
    }

    @Override
    public String toString(){
        return String.format("[%d] - %s", index, value);
    }
}
