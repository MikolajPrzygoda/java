import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("Podaj numer pesel: ");

        Scanner in = new Scanner(System.in);
        Pesel pesel = new Pesel(in.next());
        if(pesel.check())
            System.out.print("Podano poprawny numer pesel");
        else
            System.out.print("Podano błędny numer pesel");
    }
}
