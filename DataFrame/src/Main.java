import values.Integer;

public class Main{

    /**
     * TODO:
     * Dodaj do swojej implementacji DataFrame możliwość wczytywania danych z pliku
     */

    public static void main(String[] args){
        
        DataFrame df = new DataFrame(new String[]{"kol1", "kol2"}, new Class[]{Integer.class, Integer.class});
        df.add("1", "2");
        df.add("0", "4");
        df.add("5", "0");
        df.add("7", "8");
        System.out.println(df);

        SparseDataFrame sdf = new SparseDataFrame(df, "0");
        System.out.println(sdf);
    }
}
