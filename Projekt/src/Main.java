import values.*;

public class Main {

    public static void main(String[] args) {

        DataFrame df = new DataFrame("groupby.csv", new Class[]{StringV.class, DateTimeV.class, DoubleV.class, DoubleV.class});
        System.out.println(
                df
                        .groupBy(new String[]{"id"})
                        .max()
                        .sortBy("id")
        );

    }
}
