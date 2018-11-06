package values;

public class COOValue extends Value {

    private int index;
    private Value value;

    public COOValue(int index, Value value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public Value getValue() {
        return value;
    }

    //not supported (at least for now)
//    /**
//     * Create COOValue pair from string.
//     * @param s - String in the form of: "index - value_string_representation"
//     * @return Created pair.
//     */
    @Override
    public Value create(String s) {
//        String[] ss = s.split(" - ");
//        return new COOValue(Integer.valueOf(ss[0]), new Value(ss[1]));
        return null;
    }

    @Override
    public Value add(Value other) {
        if (other instanceof COOValue) {
            Value result = value.clone();
            result.add(((COOValue) other).value);
            return new COOValue(index, result);
        } else {
            System.out.println("Tried adding non-COOValue to COOValue. Returned null");
            return null;
        }
    }

    @Override
    public Value sub(Value other) {
        if (other instanceof COOValue) {
            Value result = value.clone();
            result.sub(((COOValue) other).value);
            return new COOValue(index, result);
        } else {
            System.out.println("Tried subtracting non-COOValue to COOValue. Returned null");
            return null;
        }
    }

    @Override
    public Value mul(Value other) {
        if (other instanceof COOValue) {
            Value result = value.clone();
            result.mul(((COOValue) other).value);
            return new COOValue(index, result);
        } else {
            System.out.println("Tried multiplying non-COOValue to COOValue. Returned null");
            return null;
        }
    }

    @Override
    public Value div(Value other) {
        if (other instanceof COOValue) {
            Value result = value.clone();
            result.div(((COOValue) other).value);
            return new COOValue(index, result);
        } else {
            System.out.println("Tried dividing non-COOValue to COOValue. Returned null");
            return null;
        }
    }

    @Override
    public Value pow(Value other) {
        if (other instanceof COOValue) {
            Value result = value.clone();
            result.pow(((COOValue) other).value);
            return new COOValue(index, result);
        } else {
            System.out.println("Tried raising to a power non-COOValue to COOValue. Returned null");
            return null;
        }
    }

    @Override
    public boolean eq(Value other) {
        return this.equals(other);
    }

    @Override
    public boolean lte(Value other) {
        if (other instanceof COOValue) {
            return value.lte(((COOValue) other).value);
        } else {
            return value.lte(other);
        }
    }

    @Override
    public boolean gte(Value other) {
        if (other instanceof COOValue) {
            return value.gte(((COOValue) other).value);
        } else {
            return value.gte(other);
        }
    }

    @Override
    public boolean neq(Value other) {
        return !this.equals(other);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof COOValue) {
            return value.eq(((COOValue) obj).value);
        } else {
            return false;
        }
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
