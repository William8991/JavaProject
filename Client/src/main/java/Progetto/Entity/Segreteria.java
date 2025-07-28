package Progetto.Entity;

import java.time.LocalDate;

public class Segreteria extends Dipendente{

    public Segreteria(String nome_persona, String cognome_persona, LocalDate data_nascita, String citta_persona, String residenza_persona, String codice_fiscale, String email_persona, String cellulare_persona, String username, String password, int matricola_dipendente, String email_ospedaliera, String sede_ospedale, int stripendio_dipendente, String professione, LocalDate termine_contratto){
        super(nome_persona, cognome_persona, data_nascita, citta_persona, residenza_persona, codice_fiscale, email_persona, cellulare_persona, username,password, email_ospedaliera, sede_ospedale, stripendio_dipendente, professione, termine_contratto);
    }
}
