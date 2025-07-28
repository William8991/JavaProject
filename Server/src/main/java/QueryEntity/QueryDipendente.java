package QueryEntity;

import java.util.ArrayList;

public class QueryDipendente {

    public static String visualizzaAppuntamenti(ArrayList<String> parametri){
        return "SELECT t.id_prenotazione , d.nome_medico , d.cognome_medico, p.nome_paziente, p.cognome_paziente, d.professione_medico, t.data_ora_visita, t.sede_ospedale, t.tipo_visita, t.costo_visita, t.stato_pagamento\n" +
                "FROM prenotazioni t\n" +
                "JOIN paziente p ON t.codice_fiscale_paziente= p.codice_fiscale\n" +
                "JOIN dipendente d ON t.codice_fiscale_dipendente= d.codice_fiscale\n" +
                "WHERE t.codice_fiscale_dipendente = '" + parametri.get(2) + "';";
    }

    public static String visualizzaAppuntamento(ArrayList<String> parametri){
        return "SELECT t.id_prenotazione , d.nome_medico , d.cognome_medico, p.nome_paziente, p.cognome_paziente, d.professione_medico, t.data_ora_visita, t.sede_ospedale, t.tipo_visita, t.costo_visita, t.stato_pagamento\n" +
                "FROM prenotazioni t\n" +
                "JOIN paziente p ON t.codice_fiscale_paziente= p.codice_fiscale\n" +
                "JOIN dipendente d ON t.codice_fiscale_dipendente= d.codice_fiscale\n" +
                "WHERE t.id_prenotazione = '" + parametri.get(2) + "';";
    }

    public static String reuperaCodFiscalePaziente(ArrayList<String> parametri) {
        return "SELECT * FROM prenotazioni WHERE id_prenotazione ="+ parametri.get(2)+";";
    }

    public static String eliminaAppuntamento(ArrayList<String> parametri) {
        return "DELETE FROM prenotazioni WHERE id_prenotazione ="+parametri.get(2)+";";
    }

}
