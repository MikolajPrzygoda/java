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
        if (other instanceof DoubleV)
            return new IntegerV((int) (value + Double.valueOf(other.toString())));
        else if (other instanceof FloatV)
            return new IntegerV((int) (value + Float.valueOf(other.toString())));
        else if (other instanceof IntegerV)
            return new IntegerV(value + Integer.valueOf(other.toString()));
        else {
            System.out.println("Tried adding number value to a non-number value. Returned null");
            return null;
        }
    }

    @Override
    public Value sub(Value other) {
        if (other instanceof DoubleV)
            return new IntegerV((int) (value - Double.valueOf(other.toString())));
        else if (other instanceof FloatV)
            return new IntegerV((int) (value - Float.valueOf(other.toString())));
        else if (other instanceof IntegerV)
            return new IntegerV(value - Integer.valueOf(other.toString()));
        else {
            System.out.println("Tried subtracting number value from a non-number value. Returned null");
            return null;
        }
    }

    @Override
    public Value mul(Value other) {
        if (other instanceof DoubleV)
            return new IntegerV((int) (value * Double.valueOf(other.toString())));
        else if (other instanceof FloatV)
            return new IntegerV((int) (value * Float.valueOf(other.toString())));
        else if (other instanceof IntegerV)
            return new IntegerV(value * Integer.valueOf(other.toString()));
        else {
            System.out.println("Tried multiplying number value by a non-number value. Returned null");
            return null;
        }
    }

    @Override
    public Value div(Value other) {
        if (other instanceof DoubleV)
            return new IntegerV((int) (value / Double.valueOf(other.toString())));
        else if (other instanceof FloatV)
            return new IntegerV((int) (value / Float.valueOf(other.toString())));
        else if (other instanceof IntegerV)
            return new IntegerV(value / Integer.valueOf(other.toString()));
        else {
            System.out.println("Tried dividing number value by a non-number value. Returned null");
            return null;
        }
    }

    @Override
    public Value pow(Value other) {
        if (other instanceof DoubleV)
            return new DoubleV(Math.pow(value, Double.valueOf(other.toString())));
        else if (other instanceof FloatV)
            return new DoubleV(Math.pow(value, Float.valueOf(other.toString())));
        else if (other instanceof IntegerV)
            return new DoubleV(Math.pow(value, Integer.valueOf(other.toString())));
        else {
            System.out.println("Tried raising number value to a non-number value. Returned null");
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
        return Integer.hashCode(value);
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
