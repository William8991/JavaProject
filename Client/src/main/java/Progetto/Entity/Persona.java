package Progetto.Entity;// Importo la libreria per inserire la data di nascita

import java.time.LocalDate;

public class Persona {
    // Dichiaro gli attributi della persona (privati)
    private String nome_persona;
    private String cognome_persona;
    private LocalDate data_nascita;
    private String citta_persona;
    private String residenza_persona;
    private String codice_fiscale;
    private String email_persona;
    private String cellulare_persona;

    // Costruttore per inizializzare gli attributi
    public Persona(String nome_persona, String cognome_persona, LocalDate data_nascita, String citta_persona, String residenza_persona, String codice_fiscale, String email_persona, String cellulare_persona){
        this.nome_persona=nome_persona;
        this.cognome_persona=cognome_persona;
        this.data_nascita=data_nascita;
        this.citta_persona=citta_persona;
        this.residenza_persona=residenza_persona;
        this.codice_fiscale=codice_fiscale;
        this.email_persona=email_persona;
        this.cellulare_persona=cellulare_persona;
    }
    // Get e Setter per modificare gli attributi
    public String getNome_persona(){
        return nome_persona;
    }
    public String getCognome_persona(){
        return cognome_persona;
    }
    public LocalDate getData_nascita(){
        return data_nascita;
    }
    public String getCitta_persona(){
        return citta_persona;
    }
    public String getResidenza(){
        return residenza_persona;
    }
    public String getCodice_fiscale(){
        return codice_fiscale;
    }
    public String getEmail_persona(){
        return email_persona;
    }
    public String getCellulare_persona(){
        return cellulare_persona;
    }
    public void setNome_persona(String nome){
        this.nome_persona=nome;
    }
    public void setCognome_persona(String cognome){
        this.cognome_persona=cognome;
    }
    public void setData_nascita(LocalDate data){
        this.data_nascita=data_nascita;
    }
    public void setCitta_persona(String citta){
        this.citta_persona=citta;
    }
    public void setResidenza_persona(String residenza){
        this.residenza_persona=residenza;
    }
    public void setCodice_fiscale(String codiceF){
        this.codice_fiscale=codiceF;
    }
    public void setEmail_persona(String email){
        this.email_persona=email;
    }
    public void setCellulare_persona(String cellulare) {
        this.cellulare_persona = cellulare;
    }

    // Metodo per stampare la persona
    public void StampaPersona(){
        System.out.println("Nome: "+getNome_persona());
        System.out.println("Cognome: "+getCognome_persona());
        System.out.println("Data di nascita: "+getData_nascita());
        System.out.println(("Città: "+getCitta_persona()));
        System.out.println("Residenza: "+getResidenza());
        System.out.println("Codice fiscale: "+getCodice_fiscale());
        System.out.println("Email: "+getEmail_persona());
        System.out.println("Cellulare: "+getCellulare_persona());
    }

}
