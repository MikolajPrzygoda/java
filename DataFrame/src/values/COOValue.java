package values;

public class COOValue extends Value {

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
    public Value create(String s) {
        return null;
    }

    @Override
    public Value add(Value other) {
        return null;
    }

    @Override
    public Value sub(Value other) {
        return null;
    }

    @Override
    public Value mul(Value other) {
        return null;
    }

    @Override
    public Value div(Value other) {
        return null;
    }

    @Override
    public Value pow(Value other) {
        return null;
    }

    @Override
    public boolean eq(Value other) {
        return false;
    }

    @Override
    public boolean lte(Value other) {
        return false;
    }

    @Override
    public boolean gte(Value other) {
        return false;
    }

    @Override
    public boolean neq(Value other) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public COOValue clone() {
        return new COOValue(index, value.clone());
    }

    @Override
    public String toString() {
        return String.format("[%d] - %s", index, value);
    }
}
