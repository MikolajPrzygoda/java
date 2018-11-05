import values.DateTimeV;
import values.DoubleV;
import values.IntegerV;
import values.StringV;

public class Main{

    public static void main(String[] args){

        DataFrame df = new DataFrame("groupby.csv", new Class[]{StringV.class, DateTimeV.class, DoubleV.class, DoubleV.class});
        System.out.println(df);

//        DataFrameGroup dfg = df.groupBy(new String[]{"kol1"});
//        System.out.println(dfg);
    }
}
