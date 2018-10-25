package values;

public class Double extends Value{

    private double value;

    public Double(){}

    public Double(double value) {
        this.value = value;
    }

    @Override
    public Value create(java.lang.String s) {
        return new Double(java.lang.Double.valueOf(s));
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
        if(obj instanceof java.lang.Double)
            return (java.lang.Double) obj == value;
        else
            return false;
    }

    @Override
    public java.lang.String toString() {
        return java.lang.String.valueOf(value);
    }
}
