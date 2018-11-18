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

    //<not supported>
    @Override
    public Value add(Value other) {
        return new DateTimeV("1234-12-12");
    }

    @Override
    public Value sub(Value other) {
        return new DateTimeV("1234-12-12");
    }

    @Override
    public Value mul(Value other) {
        return new DateTimeV("1234-12-12");
    }

    @Override
    public Value div(Value other) {
        return new DateTimeV("1234-12-12");
    }

    @Override
    public Value pow(Value other) {
        return new DateTimeV("1234-12-12");
    }
    //</not supported>

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
        return value.hashCode();
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
        String month = String.valueOf(value.getMonthValue());
        String day = String.valueOf(value.getDayOfMonth());

        month = month.length() == 1 ? "0" + month : month;
        day = day.length() == 1 ? "0" + day : day;

        return value.getYear() + "-" + month + "-" + day;
    }
}
