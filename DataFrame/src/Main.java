public class Main{

    /**
     * TODO:
     * Dodaj do swojej implementacji DataFrame możliwość wczytywania danych z pliku
     */

    public static void main(String[] args){
        SparseDataFrame sdf = new SparseDataFrame(new String[]{"kol1", "kol2", "kol3"}, "int", "0");
        sdf.add(1, 2, 3);
        sdf.add(0, 0, 0);
        sdf.add(0, 0, 0);
        sdf.add(4, 5, 6);
        sdf.add(0, 0, 0);
        sdf.add(0, 0, 0);
        sdf.add(7, 8, 9);
        System.out.println(sdf);
        DataFrame df = new DataFrame(sdf);
        System.out.println(df);
        System.out.println(new SparseDataFrame(df, "0"));

    }
}
