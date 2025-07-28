package Routine;

public class RoutineQuery {

    public static String ricercaNumeroMedici(){
        return "SELECT(codice_fiscale)\n" +
                "FROM dipendente\n" +
                "WHERE professione_medico <> 'Segreteria';"; //ho bisogno del numero per fare il for
    }

    public static String ricercaCodiceFiscaleDipendenti(){
        return "SELECT(codice_fiscale)\n" +
                "FROM dipendente\n" +
                "WHERE professione_medico <> 'Segreteria';"; //li ho bisogno per parametrizzare la tabella_codicefiscale nelle query scritta qua sotto
    }


    public static String  eliminazionePrimaDataQuery(String codMedico){
        return "DELETE FROM tabella_"+codMedico+"\n" +
                "ORDER BY data_disp ASC\n" +
                "LIMIT 1;";
    }

    public static String  inseriscoProssimaDataQuery(String codMedico){
        return "INSERT INTO tabella_"+codMedico+" (data_disp)\n" +
                "SELECT DATE_ADD(MAX(data_disp), INTERVAL 1 DAY) \n" +
                "FROM tabella_"+codMedico+";";
    }

    public static String OttieniIdPrenotazioniObsoleteQuery(){
        return "SELECT p.id_prenotazione\n" +
                "FROM prenotazioni p\n" +
                "WHERE p.data_ora_visita < CONVERT_TZ(NOW(), 'UTC', 'Europe/Rome');";
    }

    public static String eliminazionePrenotazioniObsoleteQuery(){
        return "DELETE \n" +
                "FROM prenotazioni p\n" +
                "WHERE p.data_ora_visita < CONVERT_TZ(NOW(), 'UTC', 'Europe/Rome');";
    }



}

