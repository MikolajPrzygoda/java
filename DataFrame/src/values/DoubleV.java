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
        if (other instanceof DoubleV)
            return new DoubleV(value + ((DoubleV) other).value);
        else {
            System.out.println("Tried adding non-DoubleV to DoubleV. Returned null");
            return null;
        }
    }

    @Override
    public Value sub(Value other) {
        if (other instanceof DoubleV)
            return new DoubleV(value - ((DoubleV) other).value);
        else {
            System.out.println("Tried subtracting non-DoubleV from DoubleV. Returned null");
            return null;
        }
    }

    @Override
    public Value mul(Value other) {
        if (other instanceof DoubleV)
            return new DoubleV(value * ((DoubleV) other).value);
        else {
            System.out.println("Tried multiplying DoubleV with non-DoubleV. Returned null");
            return null;
        }
    }

    @Override
    public Value div(Value other) {
        if (other instanceof DoubleV)
            return new DoubleV(value / ((DoubleV) other).value);
        else {
            System.out.println("Tried dividing DoubleV by a non-DoubleV. Returned null");
            return null;
        }
    }

    @Override
    public Value pow(Value other) {
        if (other instanceof DoubleV)
            return new DoubleV(Math.pow(value, ((DoubleV) other).value));
        else {
            System.out.println("Tried raising DoubleV to a non-DoubleV power. Returned null");
            return null;
        }
    }

    @Override
    public boolean eq(Value other) {
        return this.equals(other);
    }

    @Override
    public boolean lte(Value other) {
        if (other instanceof DoubleV)
            return value <= ((DoubleV) other).value;
        else {
            System.out.println("Tried comparing DoubleV with a non-DoubleV. Returned false");
            return false;
        }
    }

    @Override
    public boolean gte(Value other) {
        if (other instanceof DoubleV)
            return value >= ((DoubleV) other).value;
        else {
            System.out.println("Tried comparing DoubleV with a non-DoubleV. Returned false");
            return false;
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
        if (obj instanceof DoubleV)
            return ((DoubleV) obj).value == value;
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
