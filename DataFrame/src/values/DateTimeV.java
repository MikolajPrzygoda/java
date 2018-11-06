package values;

import java.time.LocalDate;

public class DateTimeV extends Value {

    private LocalDate value;

    public DateTimeV(LocalDate value) {
        this.value = value;
    }

    public DateTimeV(String s) {
        String[] date = s.split("-");
        value = LocalDate.of(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
    }

    @Override
    public Value create(String s) {
        return new DateTimeV(s);
    }

    //not supported
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
    //==============

    @Override
    public boolean eq(Value other) {
        return this.equals(other);
    }

    @Override
    public boolean lte(Value other) {
        return value.isBefore(((DateTimeV) other).value) || value.isEqual(((DateTimeV) other).value);
    }

    @Override
    public boolean gte(Value other) {
        return value.isAfter(((DateTimeV) other).value) || value.isEqual(((DateTimeV) other).value);

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
        if (obj instanceof DateTimeV)
            return value.isEqual(((DateTimeV) obj).value);
        else
            return false;
    }

    @Override
    public DateTimeV clone() {
        return new DateTimeV(value);
    }

    @Override
    public String toString() {
        return value.getYear() + "-" + value.getMonthValue() + "-" + value.getDayOfWeek().getValue();
    }
}
