import values.IntegerV;

public class Main{

    /**
     * TODO:
     * Dodaj do swojej implementacji DataFrame możliwość wczytywania danych z pliku
     */

    public static void main(String[] args){

        DataFrame df = new DataFrame(new String[]{"kol1", "kol2", "kol3"}, new Class[]{IntegerV.class, IntegerV.class, IntegerV.class});
        df.add("1", "1", "20");
        df.add("0", "2", "19");
        df.add("1", "3", "18");
        df.add("5", "4", "17");
        df.add("0", "5", "16");
        df.add("1", "6", "15");
        df.add("0", "7", "14");
        df.add("5", "8", "13");
        df.add("7", "9", "12");
        df.add("0", "10", "11");
        df.add("7", "11", "10");
        df.add("5", "12", "9");
        df.add("5", "13", "8");
        df.add("1", "14", "7");
        df.add("7", "15", "6");
        df.add("1", "16", "5");
        df.add("5", "17", "4");
        df.add("7", "18", "3");
        System.out.println(df);

        DataFrameGroup dfg = df.groupBy(new String[]{"kol1"});
        System.out.println(dfg);
    }
}
