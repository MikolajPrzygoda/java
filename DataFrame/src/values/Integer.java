package values;

public class Integer extends Value{

    private int value;

    public Integer(){}

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public Value create(java.lang.String s) {
        return new Integer(java.lang.Integer.valueOf(s));
    }

    @Override
    public Value add(Value other) {
        return new Integer(value + ((Integer) other).value);
    }

    @Override
    public Value sub(Value other) {
        return new Integer(value - ((Integer) other).value);
    }

    @Override
    public Value mul(Value other) {
        return new Integer(value * ((Integer) other).value);
    }

    @Override
    public Value div(Value other) {
        return new Integer(value / ((Integer) other).value);
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
        if(obj instanceof java.lang.Integer)
            return (java.lang.Integer) obj == value;
        else
            return false;
    }

    @Override
    public java.lang.String toString() {
        return java.lang.String.valueOf(value);
    }
}
