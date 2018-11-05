package values;

public class StringV extends Value {

    private String value;

    public StringV(String value) {
        this.value = value;
    }

    @Override
    public Value create(String s) {
        return new StringV(s);
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
    public StringV clone() {
        return new StringV(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
