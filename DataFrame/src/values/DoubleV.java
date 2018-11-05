package values;

public class DoubleV extends Value {

    private double value;

    public DoubleV(double value) {
        this.value = value;
    }

    public DoubleV(String s) {
        this.value = Double.valueOf(s);
    }

    @Override
    public Value create(String s) {
        return new DoubleV(s);
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
        if (obj instanceof Double)
            return (Double) obj == value;
        else
            return false;
    }

    @Override
    public DoubleV clone() {
        return new DoubleV(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
