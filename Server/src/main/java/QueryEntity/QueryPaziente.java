package QueryEntity;

import java.util.ArrayList;

public class QueryPaziente {

    public static String cercaUsername(ArrayList<String> parametri) {
        return "SELECT * FROM utente WHERE username='"+parametri.get(2)+"';";
    }
    public static String cercaUsernamePassword(ArrayList<String> parametri) {
        return "SELECT codice_fiscale FROM utente WHERE username ='"+parametri.get(2)+"' AND password_utente = '"+parametri.get(3)+"';";
    }
    public static String viasualizzaTickets(ArrayList<String> parametri){
        return "SELECT t.id_prenotazione , d.nome_medico , d.cognome_medico, p.nome_paziente, p.cognome_paziente, d.professione_medico, t.data_ora_visita, t.sede_ospedale, t.tipo_visita, t.costo_visita, t.stato_pagamento\n" +
                "FROM prenotazioni t\n" +
                "JOIN paziente p ON t.codice_fiscale_paziente= p.codice_fiscale\n" +
                "JOIN dipendente d ON t.codice_fiscale_dipendente= d.codice_fiscale\n" +
                "WHERE t.codice_fiscale_paziente = '" + parametri.get(2) + "';";
    }
    public static String cancellaPrenotazione(ArrayList<String> parametri) {
        return "DELETE FROM prenotazioni\n" +
                "WHERE codice_fiscale_paziente = '" + parametri.get(2) + "';";
    }
    public static String aggiungiPrenotazione(ArrayList<String> parametri) {
        return "INSERT INTO prenotazioni(codice_fiscale_paziente,codice_fiscale_dipendente,data_ora_visita,sede_ospedale,tipo_visita,costo_visita,stato_pagamento)\n" +
                "VALUES \n" +
                "('" + parametri.get(2) + "','" + parametri.get(3) + "','" + parametri.get(4) + "','" + parametri.get(5) + "','" + parametri.get(6) + "'," + parametri.get(7) + ",'da pagare');";
    }

    public static String contrassegnaMedico(ArrayList<String> parametri) {
        return "UPDATE tabella_"+parametri.get(2)+"\n" +
                "SET "+parametri.get(3)+" = 'x'\n" +
                "WHERE data_disp = '"+ parametri.get(4) +"';";
    }

    public static String selezionaDataPrenotazione(ArrayList<String> parametri) {
        return "UPDATE tabella_"+parametri.get(2)+"\n" +
                "SET "+ parametri.get(3) +" = 'occupato'\n" +
                "WHERE data_disp = (SELECT DATE(data_ora_visita) \n" +
                "                                     FROM prenotazioni\n" +
                "                                     WHERE id_prenotazione = (SELECT id_prenotazione \n" +
                "                                                                                  FROM prenotazioni \n" +
                "                                                                                  ORDER BY id_prenotazione DESC \n" +
                "                                                                                   LIMIT 1)); ";
    }
    public static String aggiornamentoStatoPagamento(ArrayList<String> parametri) {
        return "UPDATE prenotazioni\n" +
                "SET stato_pagamento = 'pagato'\n" +
                "WHERE id_prenotazione = "+parametri.get(2)+";";
    }
    public static String statoPagamento(ArrayList<String> parametri) {
        return "SELECT stato_pagamento \n" +
                "FROM prenotazioni\n" +
                "WHERE id_prenotazione = "+parametri.get(2)+";";
    }

    public static String listaRuoli() {
        return "SELECT DISTINCT(professione_medico)\n" +
                "FROM dipendente\n"+
                "WHERE professione_medico <> 'Segreteria';";
    }

    public static String listaMedici(ArrayList<String> parametri) {
        return "SELECT d.codice_fiscale\n" +
                "FROM dipendente d\n" +
                "WHERE d.professione_medico='"+ parametri.get(2) +"';";
    }

    public static String trovaOrarioDataMedico(ArrayList<String> parametri) {
        return "SELECT o."+parametri.get(2)+"\n" +
                "FROM tabella_"+ parametri.get(3) +" o\n" +
                "WHERE data_disp= '"+parametri.get(4)+"';";
    }

    public static String nomeMedico(ArrayList<String> parametri) {
        return "SELECT nome_medico, cognome_medico, sede_ospedale FROM dipendente WHERE codice_fiscale = '"+ parametri.get(2) +"';";
    }

    public static String nomePaziente(ArrayList<String> parametri) {
        return "SELECT nome_paziente, cognome_paziente FROM paziente WHERE codice_fiscale = '"+ parametri.get(2) +"';";
    }

    public static String togliContrassegno(ArrayList<String> parametri) {
        return "UPDATE tabella_"+parametri.get(2)+"\n" +
                "SET "+parametri.get(3)+" =''\n" +
                "WHERE data_disp='"+parametri.get(4)+"';";
    }

    public static String recuperaParametriContrassegno(ArrayList<String> parametri) {
        return "SELECT codice_fiscale_dipendente, DATE(data_ora_visita), TIME(data_ora_visita)\n" +
                "FROM prenotazioni\n" +
                "WHERE id_prenotazione = "+parametri.get(2)+" ;";
    }

    public static String checkAppuntamento(ArrayList<String> parametri) {
        return "SELECT * FROM prenotazioni WHERE id_prenotazione = " + parametri.get(2) +";";
    }

}
