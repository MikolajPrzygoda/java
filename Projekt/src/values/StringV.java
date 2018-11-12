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
        if (other instanceof StringV)
            return new StringV(value + ((StringV) other).value);
        else
            return new StringV("null");
    }
    
    //not supported
    @Override
    public Value sub(Value other) {
        return new StringV("null");
    }

    @Override
    public Value mul(Value other) {
        return new StringV("null");
    }

    @Override
    public Value div(Value other) {
        return new StringV("null");
    }

    @Override
    public Value pow(Value other) {
        return new StringV("null");
    }
    //=============
    
    @Override
    public boolean eq(Value other) {
        return this.equals(other);
    }

    @Override
    public boolean lte(Value other) {
        if (other instanceof StringV)
            return value.compareTo(((StringV) other).value) <= 0;
        else {
            System.out.println("Tried comparing StringV with a non-StringV. Returned false");
            return false;
        }
    }

    @Override
    public boolean gte(Value other) {
        if (other instanceof StringV)
            return value.compareTo(((StringV) other).value) >= 0;
        else {
            System.out.println("Tried comparing StringV with a non-StringV. Returned false");
            return false;
        }
    }

    @Override
    public boolean neq(Value other) {
        return !this.equals(other);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringV)
            return value.equals(((StringV) obj).value);
        else
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
