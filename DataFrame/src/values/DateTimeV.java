package values;

import java.time.LocalDate;
import java.util.Date;

public class DateTimeV extends Value {

    private LocalDate value;

    public DateTimeV(LocalDate value) {
        this.value = value;
    }

    public DateTimeV(String s) {
        Date.parse(s);
    }

    @Override
    public Value create(String s) {
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
    public DateTimeV clone() {
        return new DateTimeV(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
