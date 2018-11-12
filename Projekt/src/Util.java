import java.io.*;

public class Util {
    public static String[] loadFile(String name) {
        StringBuilder sb = new StringBuilder();

        InputStream is = Main.class.getResourceAsStream(name);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString().split("\n");
    }
}
