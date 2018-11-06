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
        if (other instanceof IntegerV)
            return new IntegerV(value + ((IntegerV) other).value);
        else {
            System.out.println("Tried adding non-IntegerV to IntegerV. Returned null");
            return null;
        }
    }

    @Override
    public Value sub(Value other) {
        if (other instanceof IntegerV)
            return new IntegerV(value - ((IntegerV) other).value);
        else {
            System.out.println("Tried subtracting non-IntegerV from IntegerV. Returned null");
            return null;
        }
    }

    @Override
    public Value mul(Value other) {
        if (other instanceof IntegerV)
            return new IntegerV(value * ((IntegerV) other).value);
        else {
            System.out.println("Tried multiplying IntegerV with non-IntegerV. Returned null");
            return null;
        }
    }

    @Override
    public Value div(Value other) {
        if (other instanceof IntegerV)
            return new IntegerV(value / ((IntegerV) other).value);
        else {
            System.out.println("Tried dividing IntegerV by a non-IntegerV. Returned null");
            return null;
        }
    }

    @Override
    public Value pow(Value other) {
        if (other instanceof IntegerV)
            return new IntegerV((int) Math.pow(value, ((IntegerV) other).value));
        else {
            System.out.println("Tried raising IntegerV to a non-IntegerV power. Returned null");
            return null;
        }
    }

    @Override
    public boolean eq(Value other) {
        return this.equals(other);
    }

    @Override
    public boolean lte(Value other) {
        if (other instanceof IntegerV)
            return value <= ((IntegerV) other).value;
        else {
            System.out.println("Tried comparing IntegerV with a non-IntegerV. Returned false");
            return false;
        }
    }

    @Override
    public boolean gte(Value other) {
        if (other instanceof IntegerV)
            return value >= ((IntegerV) other).value;
        else {
            System.out.println("Tried comparing IntegerV with a non-IntegerV. Returned false");
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
        if (obj instanceof IntegerV)
            return ((IntegerV) obj).value == value;
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
