import values.DateTimeV;
import values.DoubleV;
import values.IntegerV;
import values.StringV;

public class Main {

    public static void main(String[] args) {

//        DataFrame df = new DataFrame("groupby.csv", new Class[]{StringV.class, DateTimeV.class, DoubleV.class, DoubleV.class});
//        DataFrameGroup dfg = df.groupBy(new String[]{"id"});
//        System.out.println(dfg);

        DataFrame df = new DataFrame(
                new String[]{"Integer", "Double", "String", "DateTime"},
                new Class[]{IntegerV.class, DoubleV.class, StringV.class, DateTimeV.class});

        df.add("123",   "123.123456", "TESTTEST", "1999-07-02");
        df.add("1234",  "123.12345",  "TESTTES",  "2000-08-01");
        df.add("12345", "123.1234",   "TESTTE",   "2001-09-30");
        df.add("1234",  "123.123",    "TESTT",    "2000-10-29");
        df.add("123",   "123.12",     "TEST",     "1999-11-28");
        df.add("12",    "123.1",      "TES",      "1998-12-27");

        System.out.println(df);

    }
}
