@SuppressWarnings("WeakerAccess")
public class Pesel {

    private String pesel;

    /**
     * There's no validation in the constructor so always use check() before anything else.
     * @param pesel Pesel number to store.
     */
    public Pesel(String pesel) {
        this.pesel = pesel;
    }

    /**
     * Checks whether supplied pesel is valid.
     * @return result of the check.
     */
    public boolean check(){
        return _check(this.pesel);
    }


    /**
     * Private helper function to check for pesel validity.
     * @param pesel pesel number to check.
     * @return result of the check.
     */
    private static boolean _check(String pesel){
        //must have 11 digits
        if(pesel.length() != 11)
            return false;

        //convert String to int[]
        char[] chars = pesel.toCharArray();
        int[] digits = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            digits[i] = (int) chars[i];
        }

        //checksum (https://pl.wikipedia.org/wiki/PESEL#Cyfra_kontrolna_i_sprawdzanie_poprawno%C5%9Bci_numeru)
        return (digits[0] + digits[1] * 3 + digits[2] * 7 + digits[3] * 9 + digits[4] + digits[5] * 3 +
                digits[6] * 7 + digits[7] * 9 + digits[8] + digits[9] * 3 + digits[10]) % 10 == 0;
    }
}
