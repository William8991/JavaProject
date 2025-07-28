package QueryEntity;

import java.util.ArrayList;

public class QuerySegreteria {
    public static String viasualizzaDatiPersonali(ArrayList<String> parametri){
        return "SELECT codice_fiscale AS 'Codice fiscale', nome AS 'Nome', cognome AS 'Cognome', data_nascita AS 'Data di nascita', luogo_nascita AS 'Città', residenza AS 'Residenza', email AS 'Email', telefono AS 'Numero di telefono'  FROM persona WHERE codice_fiscale = '" + parametri.get(2) + "';";
    }
    public static String visualizzaDatiProfessionali(ArrayList<String> parametri) {
        return "SELECT * FROM dipendente WHERE codice_fiscale = '" + parametri.get(2) + "';";
    }
    public static String visualizzaDipendenti() {
        return "SELECT * FROM dipendente;";
    }
    public static String visualizzaPazienti() {
        return "SELECT * FROM paziente;";
    }

    public static String viasualizzaPaziente(ArrayList<String> parametri){
        return "SELECT * FROM paziente WHERE codice_fiscale = '" + parametri.get(2) + "';";
    }



    public static String cercaPassword(ArrayList<String> parametri){
        return "SELECT * FROM utente WHERE codice_fiscale = '" + parametri.get(2) + "' AND password_utente = '" + parametri.get(3)+ "';";
    }




    public static String aggiungiPersona(ArrayList<String> parametri){
        return "INSERT INTO persona(codice_fiscale, nome, cognome, data_nascita, luogo_nascita, residenza, email, telefono)\n" +
                "VALUES\n" +
                "('"+parametri.get(2)+"', '"+parametri.get(3)+"', '"+ parametri.get(4)+"','"+parametri.get(5)+"', '"+parametri.get(6)+"','"+parametri.get(7)+"', '"+parametri.get(8)+"', '"+parametri.get(9)+"');";
    }
    public static String aggiungiUtente(ArrayList<String> parametri){
        return "INSERT INTO utente "+
                "VALUES\n" +
                "('"+parametri.get(2)+"', '"+parametri.get(3)+"', '"+ parametri.get(4)+"');";
    }
    public static String aggiungiPaziente(ArrayList<String> parametri){
        return "INSERT INTO paziente "+
                "VALUES\n" +
                "('"+parametri.get(2)+"', '"+parametri.get(3)+"', '"+ parametri.get(4)+"', default);";
    }
    public static String aggiungiDipendente(ArrayList<String> parametri){
        return "INSERT INTO dipendente (codice_fiscale, nome_medico, cognome_medico, stipendio, sede_ospedale, professione_medico, data_termine_contratto)"+
                "VALUES\n" +
                "('"+parametri.get(2)+"', '"+parametri.get(3)+"', '"+ parametri.get(4)+"',"+ parametri.get(5) + ",'" + parametri.get(6) + "','" + parametri.get(7) + "','" + parametri.get(8) + "');";
    }
    public static String creaTabellaMedico(ArrayList<String> parametri) {
        return "CREATE TABLE tabella_"+parametri.get(2)+"(\n" +
                "  data_disp DATE not null PRIMARY KEY,\n" +
                "  ora8 varchar(1) not null DEFAULT '' CHECK (ora8 IN ('', 'x')),\n" +
                "  ora9 varchar(1) not null DEFAULT '' CHECK (ora9 IN ('', 'x')),\n" +
                "  ora10 varchar(1) not null DEFAULT '' CHECK (ora10 IN ('', 'x')),\n" +
                "  ora11 varchar(1) not null DEFAULT '' CHECK (ora11 IN ('', 'x')),\n" +
                "  ora12 varchar(1) not null DEFAULT '' CHECK (ora12 IN ('', 'x')),\n" +
                "  ora15 varchar(1) not null DEFAULT '' CHECK (ora15 IN ('', 'x')),\n" +
                "  ora16 varchar(1) not null DEFAULT '' CHECK (ora16 IN ('', 'x')),\n" +
                "  ora17 varchar(1) not null DEFAULT '' CHECK (ora17 IN ('', 'x')),\n" +
                "  ora18 varchar(1) not null DEFAULT '' CHECK (ora18 IN ('', 'x')),\n" +
                "  ora19 varchar(1) not null DEFAULT '' CHECK (ora19 IN ('', 'x'))\n" +
                " );";

    }

    public static String inserisciRecordsTabella(ArrayList<String> parametri) {
        return "INSERT INTO tabella_"+ parametri.get(2) +"(data_disp)\n" +
                "VALUES\n" +
                "('2025-01-01'),\n" +
                "('2025-01-02'),\n" +
                "('2025-01-03'),\n" +
                "('2025-01-04'),\n" +
                "('2025-01-05'),\n" +
                "('2025-01-06'),\n" +
                "('2025-01-07'),\n" +
                "('2025-01-08'),\n" +
                "('2025-01-09'),\n" +
                "('2025-01-10'),\n" +
                "('2025-01-11'),\n" +
                "('2025-01-12'),\n" +
                "('2025-01-13'),\n" +
                "('2025-01-14'),\n" +
                "('2025-01-15'),\n" +
                "('2025-01-16'),\n" +
                "('2025-01-17'),\n" +
                "('2025-01-18'),\n" +
                "('2025-01-19'),\n" +
                "('2025-01-20'),\n" +
                "('2025-01-21'),\n" +
                "('2025-01-22'),\n" +
                "('2025-01-23'),\n" +
                "('2025-01-24'),\n" +
                "('2025-01-25'),\n" +
                "('2025-01-26'),\n" +
                "('2025-01-27'),\n" +
                "('2025-01-28'),\n" +
                "('2025-01-29'),\n" +
                "('2025-01-30'),\n" +
                "('2025-01-31');";
    }


    public static String rimuoviRecordEntita(ArrayList<String> parametri) {
        return "DELETE FROM "+ parametri.get(2) + " WHERE codice_fiscale = '"+ parametri.get(3) + "';";
    }

    public static String rimuoviPrenotazione(ArrayList<String> parametri) {
        return "DELETE FROM "+ parametri.get(2) + " WHERE codice_fiscale_dipendente = '"+ parametri.get(3) + "';";
    }

    public static String rimuoviTabellaMedico(ArrayList<String> parametri) {
        return "DROP TABLE tabella_"+parametri.get(2)+";";
    }

    public static String eliminaPersona(ArrayList<String> parametri) {
        return "DELETE FROM persona WHERE codice_fiscale='" + parametri.get(2) + "';";
    }
    public static String eliminaUtente(ArrayList<String> parametri) {
        return "DELETE FROM utente WHERE codice_fiscale='" + parametri.get(2) + "';";
    }
    public static String eliminaPaziente(ArrayList<String> parametri) {
        return "DELETE FROM paziente WHERE codice_fiscale='" + parametri.get(2) + "';";
    }



    public static String modificaCartellaClinica(ArrayList<String> parametri) {
        return "UPDATE paziente SET cartella_clinica = '" + parametri.get(3) + "' WHERE codice_fiscale = '" + parametri.get(2) + "';";
    }
    public static String modificaDatiPersonali(ArrayList<String> parametri) {
        return "UPDATE persona SET "+ parametri.get(2) +"= '" + parametri.get(3) + "' WHERE codice_fiscale = '" + parametri.get(4) + "';";
    }
    public static String modificaUsernamePassword(ArrayList<String> parametri) {
        return "UPDATE utente SET "+ parametri.get(2) +"= '" + parametri.get(3) + "' WHERE codice_fiscale = '" + parametri.get(4) + "';";
    }
    public static String modificaDatiProfessionaliPaziente(ArrayList<String> parametri) {
        return "UPDATE paziente SET "+ parametri.get(2) +"= '" + parametri.get(3) + "' WHERE codice_fiscale = '" + parametri.get(4) + "';";
    }
    public static String modificaDatiProfessionaliDipendente(ArrayList<String> parametri) {
        return "UPDATE dipendente SET "+ parametri.get(2) +"= '" + parametri.get(3) + "' WHERE codice_fiscale = '" + parametri.get(4) + "';";
    }



    public static String checkDipendente(ArrayList<String> parametri){
        return "SELECT codice_fiscale \n" +
                "FROM dipendente\n" +
                "WHERE codice_fiscale = '"+parametri.get(2)+"';";
    }
    public static String checkSegreteria(ArrayList<String> parametri){
        return "SELECT codice_fiscale \n" +
                "FROM dipendente\n" +
                "WHERE codice_fiscale = '"+parametri.get(2)+"' AND professione_medico='Segreteria';";
    }
    public static String checkPaziente(ArrayList<String> parametri){
        return "SELECT codice_fiscale\n" +
                "FROM paziente\n" +
                "WHERE codice_fiscale = '"+parametri.get(2)+"';";
    }

    public static String checkPrenotazioni(ArrayList<String> parametri){
        return "SELECT * FROM prenotazioni WHERE codice_fiscale_dipendente = '" + parametri.get(2) + "';";
    }
}
