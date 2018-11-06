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
        if (other instanceof FloatV)
            return new FloatV(value + ((FloatV) other).value);
        else {
            System.out.println("Tried adding non-FloatV to FloatV. Returned null");
            return null;
        }
    }

    @Override
    public Value sub(Value other) {
        if (other instanceof FloatV)
            return new FloatV(value - ((FloatV) other).value);
        else {
            System.out.println("Tried subtracting non-FloatV from FloatV. Returned null");
            return null;
        }
    }

    @Override
    public Value mul(Value other) {
        if (other instanceof FloatV)
            return new FloatV(value * ((FloatV) other).value);
        else {
            System.out.println("Tried multiplying FloatV with non-FloatV. Returned null");
            return null;
        }
    }

    @Override
    public Value div(Value other) {
        if (other instanceof FloatV)
            return new FloatV(value / ((FloatV) other).value);
        else {
            System.out.println("Tried dividing FloatV by a non-FloatV. Returned null");
            return null;
        }
    }

    @Override
    public Value pow(Value other) {
        if (other instanceof FloatV)
            return new FloatV((float) Math.pow(value, ((FloatV) other).value));
        else {
            System.out.println("Tried raising FloatV to a non-FloatV power. Returned null");
            return null;
        }
    }

    @Override
    public boolean eq(Value other) {
        return this.equals(other);
    }

    @Override
    public boolean lte(Value other) {
        if (other instanceof FloatV)
            return value <= ((FloatV) other).value;
        else {
            System.out.println("Tried comparing FloatV with a non-FloatV. Returned false");
            return false;
        }
    }

    @Override
    public boolean gte(Value other) {
        if (other instanceof FloatV)
            return value >= ((FloatV) other).value;
        else {
            System.out.println("Tried comparing FloatV with a non-FloatV. Returned false");
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
        if (obj instanceof FloatV)
            return ((FloatV) obj).value == value;
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

