package values;

public class FloatV extends Value {

    private float value;

    public FloatV(float value) {
        this.value = value;
    }

    public FloatV(String s) {
        this.value = Float.valueOf(s);
    }

    @Override
    public Value create(String s) {
        return new FloatV(s);
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
        if (obj instanceof Float)
            return (Float) obj == value;
        else
            return false;
    }

    @Override
    public FloatV clone() {
        return new FloatV(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

