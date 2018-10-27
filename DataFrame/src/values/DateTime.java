package values;

import java.util.Date;

public class DateTime extends Value{

    private Date value;

    public DateTime(Date value) {
        this.value = value;
    }

    public DateTime(java.lang.String s){
        Date.parse(s);
    }

    @Override
    public Value create(java.lang.String s) {
        return null;
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
    public java.lang.String toString() {
        return value.toString();
    }

    @Override
    public DateTime clone(){
        return new DateTime(this.value);
    }
}
