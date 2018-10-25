package values;

public abstract class Value {
    public abstract Value create(java.lang.String s);
    public abstract Value add(Value other);
    public abstract Value sub(Value other);
    public abstract Value mul(Value other);
    public abstract Value div(Value other);
    public abstract Value pow(Value other);
    public abstract boolean eq(Value other);
    public abstract boolean lte(Value other);
    public abstract boolean gte(Value other);
    public abstract boolean neq(Value other);

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract java.lang.String toString();
}
