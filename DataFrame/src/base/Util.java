package base;

import values.*;

import java.io.*;

public class Util{

    //TODO: Make loadFile universal - if 'name' starts with '/' then it's an absolute path and load it as such, otherwise load it from the resources.

    /**
     * Loads a file. Returns an array of String in which every String is one line from the file.
     *
     * @param name Name of the file to load.
     */
    public static String[] loadFile(String name){
        StringBuilder sb = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(name))){
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return sb.toString().split("\n");
    }

    /**
     * Loads first n lines from a file. Returns an array of String in which every String is one line from the file.
     *
     * @param name Name of the file to load.
     */
    public static String[] loadFile(String name, int n){
        StringBuilder sb = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(name))){
            String line;
            for(int i = 0; i < n && (line = br.readLine()) != null; i++){
                sb.append(line);
                sb.append("\n");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return sb.toString().split("\n");
    }

    /**
     * Loads a file from project resources. Returns an array of String in which every String is one line from the file.
     *
     * @param name Name of the file to load.
     */
    public static String[] loadFileFromResources(String name){
        StringBuilder sb = new StringBuilder();

        InputStream is = Main.class.getResourceAsStream(name);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return sb.toString().split("\n");
    }

    /**
     * Loads the first n lines from a file from project resources. Returns an array of String in which every String
     * is one line from the file.
     *
     * @param name Name of the file to load.
     * @param n    Number of lines to load.
     */
    public static String[] loadFileFromResources(String name, int n){
        StringBuilder sb = new StringBuilder();

        InputStream is = Main.class.getResourceAsStream(name);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String line;
            for(int i = 0; i < n && (line = br.readLine()) != null; i++){
                sb.append(line);
                sb.append("\n");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return sb.toString().split("\n");
    }

    /**
     * Tries to find out what type of data it is. If the string s doesn't match int/double/date will return String.
     * ( The Date regex isn't very accurate, it may change in the future ¯\_(ツ)_/¯ )
     *
     * @param s String to find out the type of.
     * @return Type of the data represented by supplied string.
     */
    public static Class<? extends Value> inferType(String s){
        String isIntegerRegEx = "\\A-?[0-9]+\\Z";
        String isDoubleRegEx = "\\A-?[0-9]+\\.[0-9]+\\Z";
        String isDateRegEx = "\\A[0-9]{4}-[0-9]{2}-[0-9]{2}\\Z";

        if(s.matches(isIntegerRegEx))
            return IntegerV.class;
        if(s.matches(isDoubleRegEx))
            return DoubleV.class;
        if(s.matches(isDateRegEx))
            return DateTimeV.class;
        return StringV.class;
    }

    public static Class<? extends Value> stringToType(String s){
        switch(s){
            case "Integer":
                return IntegerV.class;
            case "Float":
                return FloatV.class;
            case "Double":
                return DoubleV.class;
            case "String":
                return StringV.class;
            case "Date":
                return DateTimeV.class;
            default:
                return null;
        }
    }

    public static String typeToString(Class<? extends Value> clazz){
        if(clazz == IntegerV.class)
            return "Integer";
        else if(clazz == FloatV.class)
            return "Float";
        else if(clazz == DoubleV.class)
            return "Double";
        else if(clazz == StringV.class)
            return "String";
        else if(clazz == DateTimeV.class)
            return "Date";
        return "null";
    }
}
