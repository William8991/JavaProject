package Progetto.Entity;

import java.time.LocalDate;

public class Paziente extends Utente{

    private String patologie;

    public Paziente(String nome_persona, String cognome_persona, LocalDate data_nascita, String citta_persona, String residenza_persona, String codice_fiscale, String email_persona, String cellulare_persona, String username, String password, String patologie, int id_paziente){
        super(nome_persona, cognome_persona, data_nascita, citta_persona, residenza_persona,codice_fiscale, email_persona, cellulare_persona, username, password);
        this.patologie=patologie;
    }

    public String getPatologie() {
        return patologie;
    }

    public void setPatologie(String patologie) {
        this.patologie = patologie;
    }
}
