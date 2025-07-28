package Progetto.Entity;

import java.time.LocalDate;
// Classe figlia di persona
public class Utente extends Persona{

    // Dichiaro gli attributi aggiuntivi
    private String username;
    private String password;
    public Utente(String nome_persona, String cognome_persona, LocalDate data_nascita, String citta_persona, String residenza_persona, String codice_fiscale, String email_persona, String cellulare_persona, String username, String password){
        super(nome_persona, cognome_persona, data_nascita, citta_persona, residenza_persona,codice_fiscale, email_persona, cellulare_persona);
        this.username=username;
        this.password=password;
    }

    // Get e Setter dei due attributi
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }



}
