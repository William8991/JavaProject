package Progetto.StaticClass;

import Progetto.Entity.Persona;
import Progetto.Entity.Utente;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Azione {

    public static Persona registrazionePersona() {
        Scanner ctrl = new Scanner(System.in);
        System.out.print("Nome:");
        String nome = ctrl.nextLine();
        System.out.print("Cognome:");
        String cognome = ctrl.nextLine();
        LocalDate dataNascita = null;
        while (dataNascita == null) {
            try {
                System.out.print("Data di nascita (formato: yyyy-MM-dd):");
                String dataNascitaInput = ctrl.nextLine();
                dataNascita = LocalDate.parse(dataNascitaInput); // Prova a convertire l'input in LocalDate
            } catch (DateTimeParseException e) {
                System.out.println("Formato data non valido. Riprova utilizzando il formato corretto (yyyy-MM-dd).");
            }
        }
        System.out.print("Città:");
        String citta = ctrl.nextLine();
        System.out.print("Residenza:");
        String residenza = ctrl.nextLine();
        System.out.print("Codice fiscale:");
        String codiceFiscale = ctrl.nextLine();
        System.out.print("Email:");
        String email = ctrl.nextLine();
        System.out.print("Numero di telefono:");
        String cellulare = ctrl.nextLine();
        return new Persona(nome, cognome, dataNascita, citta, residenza, codiceFiscale, email, cellulare);
    }
    public static Utente registrazioneUtente(Persona persona){
        Scanner ctrl = new Scanner(System.in);
        System.out.print("Username:");
        String username = ctrl.nextLine();
        System.out.print("Password:");
        String password = ctrl.nextLine();

        return new Utente(persona.getNome_persona(), persona.getCognome_persona(), persona.getData_nascita(), persona.getCitta_persona(),
                persona.getResidenza(), persona.getCodice_fiscale(), persona.getEmail_persona(), persona.getCellulare_persona(), username, password);
    }


    public static String[] convertDatiToArray(Object dati) {
        Field[] fields = dati.getClass().getDeclaredFields();
        String[] result = new String[fields.length];
        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                /* Ottengo il valore del campo. */
                Object value = fields[i].get(dati);
                /* Se il valore è null, lo impostiamo come "null" nella stringa.*/
                result[i] = (value != null) ? value.toString() : "null";
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
