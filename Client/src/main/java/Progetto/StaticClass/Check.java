package Progetto.StaticClass;

import java.time.LocalDate;

public class Check {
    // Metodi per il controllo dei dati
    public static boolean NomeCognomeCitta(String parola){
        if (parola.matches(".*[0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")){
            return false;
        }
        else return true;
    }
    public static boolean CodiceFiscale(String parola){
        if (parola.length()!=16 || parola.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")){
            return false;
        }
        else return true;
    }
    public static boolean Email(String parola){
        if (parola.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")){
            return true;
        }
        else return false;
    }
    public static boolean Cellulare(String parola){
        if (parola.matches("^[0-9]{10}$")){
            return true;
        }
        else return false;
    }
    public static boolean Date(LocalDate data){
        if (data==null) return false;
        if (data.isAfter(LocalDate.now())) return false;
        if (data.isBefore(LocalDate.of(1900,1,1))) return false;
        return true;
    }

    // Metodo per verificare se la password è sicura
    public static boolean Password(String password) {
        // Verifica se la lunghezza della password è almeno 8 caratteri
        if (password.length() < 8) {
            return false;
        }
        boolean hasUppercase = false;
        boolean hasSymbol = false;
        // Scorre ogni carattere della password
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            // Controlla se il carattere è una lettera maiuscola
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            }
            // Controlla se il carattere è un simbolo
            if (!Character.isLetterOrDigit(ch)) {
                hasSymbol = true;
            }
        }
        // Restituisce true solo se sono presenti sia una lettera maiuscola che un simbolo
        return hasUppercase && hasSymbol;
    }
}
