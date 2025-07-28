package Progetto.Entity;

import java.time.LocalDate;

public class Dipendente extends Utente{

    // Attributi aggiuntivi del dipendente
    private String email_ospedaliera;
    private String sede_ospedale;
    private int stripendio_dipendente;
    private String professione;
    private LocalDate termine_contratto;

    // Costruttore del dipendente
    public Dipendente(String nome_persona, String cognome_persona, LocalDate data_nascita, String citta_persona, String residenza_persona, String codice_fiscale, String email_persona, String cellulare_persona, String username, String password, String email_ospedaliera, String sede_ospedale, int stripendio_dipendente, String professione, LocalDate termine_contratto){
        super(nome_persona, cognome_persona, data_nascita, citta_persona, residenza_persona, codice_fiscale, email_persona, cellulare_persona, username,password);
        this.email_ospedaliera=email_ospedaliera;
        this.sede_ospedale=sede_ospedale;
        this.stripendio_dipendente=stripendio_dipendente;
        this.professione=professione;
        this.termine_contratto=termine_contratto;
    }

    // Get e Setter
    public String getEmail_ospedaliera() {
        return email_ospedaliera;
    }
    public void setEmail_ospedaliera(String email_ospedaliera) {
        this.email_ospedaliera = email_ospedaliera;
    }
    public String getSede_ospedale(){
        return sede_ospedale;
    }
    public void setSede_ospedale(String sede_ospedale){
        this.sede_ospedale=sede_ospedale;
    }
    public int getStripendio_dipendente() {
        return stripendio_dipendente;
    }
    public void setStripendio_dipendente(int stripendio_dipendente) {
        this.stripendio_dipendente = stripendio_dipendente;
    }
    public String getProfessione() {
        return professione;
    }
    public void setProfessione(String professione) {
        this.professione = professione;
    }
    public LocalDate getTermine_contratto() {
        return termine_contratto;
    }
    public void setTermine_contratto(LocalDate termine_contratto) {
        this.termine_contratto = termine_contratto;
    }

    // Stampa info professionale
    public void stampa_dipendente(){
        System.out.println("Email professionale: "+getEmail_ospedaliera());
        System.out.println("Sede: "+getSede_ospedale());
        System.out.println("Stipendio: "+getStripendio_dipendente());
        System.out.println("Professione: "+getProfessione());
        System.out.println("Termine del contratto: "+getTermine_contratto());
    }
}
