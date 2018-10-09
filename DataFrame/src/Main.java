public class Main {

    public static void main(String[] args) {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3"}, new String[]{"int", "float", "Test"});
        df.add(1, 2.3f, new Test());
    }
}
