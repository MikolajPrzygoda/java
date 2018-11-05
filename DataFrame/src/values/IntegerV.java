package values;

public class IntegerV extends Value {

    private int value;

    public IntegerV(int value) {
        this.value = value;
    }

    public IntegerV(String s) {
        this.value = Integer.valueOf(s);
    }

    @Override
    public Value create(String s) {
        return new IntegerV(s);
    }

    @Override
    public Value add(Value other) {
        return new IntegerV(value + ((IntegerV) other).value);
    }

    @Override
    public Value sub(Value other) {
        return new IntegerV(value - ((IntegerV) other).value);
    }

    @Override
    public Value mul(Value other) {
        return new IntegerV(value * ((IntegerV) other).value);
    }

    @Override
    public Value div(Value other) {
        return new IntegerV(value / ((IntegerV) other).value);
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
        if (obj instanceof Integer)
            return (Integer) obj == value;
        else
            return false;
    }

    @Override
    public IntegerV clone() {
        return new IntegerV(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
