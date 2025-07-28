package Server;

import QueryEntity.QueryDipendente;
import QueryEntity.QueryPaziente;
import QueryEntity.QuerySegreteria;

import java.util.ArrayList;

public class QueryCmd {

    public static String queryCheck(ArrayList<String> parametri) {
        String query = "";
        switch (parametri.get(0)) {
            case "paziente":
                switch (parametri.get(1)) {
                    case "visualizzaDatiPersonali":
                        query = QuerySegreteria.viasualizzaDatiPersonali(parametri);
                        break;
                    case "visualizzaPaziente":
                        query = QuerySegreteria.viasualizzaPaziente(parametri);
                        break;
                    case "visualizzaTickets":
                        query = QueryPaziente.viasualizzaTickets(parametri);
                        break;
                    case "cancellaPrenotazione":
                        query = QueryPaziente.cancellaPrenotazione(parametri);
                        break;
                    case "aggiungiPrenotazione":
                        query = QueryPaziente.aggiungiPrenotazione(parametri);
                        break;
                    case "contrassegnaMedico":
                        query = QueryPaziente.contrassegnaMedico(parametri);
                        break;
                    case "togliContrassegno":
                        query = QueryPaziente.togliContrassegno(parametri);
                        break;
                    case "checkAppuntamento":
                        query = QueryPaziente.checkAppuntamento(parametri);
                        break;
                    case "recuperaParametriContrassegno":
                        query = QueryPaziente.recuperaParametriContrassegno(parametri);
                        break;
                    case "selezionaDataPrenotazione":
                        query = QueryPaziente.selezionaDataPrenotazione(parametri);
                        break;
                    case "aggiornamentoStatoPagamento":
                        query = QueryPaziente.aggiornamentoStatoPagamento(parametri);
                        break;
                    case "statoPagamento":
                        query = QueryPaziente.statoPagamento(parametri);
                        break;
                    case "aggiungiPersona":
                        query = QuerySegreteria.aggiungiPersona(parametri);
                        break;
                    case "aggiungiUtente":
                        query = QuerySegreteria.aggiungiUtente(parametri);
                        break;
                    case "eliminaPersona":
                        query = QuerySegreteria.eliminaPersona(parametri);
                        break;
                    case "eliminaUtente":
                        query = QuerySegreteria.eliminaUtente(parametri);
                        break;
                    case "eliminaPaziente":
                        query = QuerySegreteria. eliminaPaziente(parametri);
                        break;
                    case "aggiungiPaziente":
                        query = QuerySegreteria.aggiungiPaziente(parametri);
                        break;
                    case "cercaUsernamePassword":
                        query = QueryPaziente.cercaUsernamePassword(parametri);
                        break;
                    case "cercaUsername":
                        query = QueryPaziente.cercaUsername(parametri);
                        break;
                    case "listaRuoli":
                        query = QueryPaziente.listaRuoli();
                        break;
                    case "listaMedici":
                        query = QueryPaziente.listaMedici(parametri);
                        break;
                    case "trovaOrarioDataMedico":
                        query = QueryPaziente.trovaOrarioDataMedico(parametri);
                        break;
                    case "nomeMedico":
                        query = QueryPaziente.nomeMedico(parametri);
                        break;
                    case "nomePaziente":
                        query = QueryPaziente.nomePaziente(parametri);
                        break;
                    default:
                        System.out.println("Errore parametro1");
                }
                break;




            case "dipendente":
                switch (parametri.get(1)) {
                    case "visualizzaAppuntamenti":
                        query = QueryDipendente.visualizzaAppuntamenti(parametri);
                        break;
                    case "visualizzaAppuntamento":
                        query = QueryDipendente.visualizzaAppuntamento(parametri);
                        break;
                    case "recuperaCodFiscalePaziente":
                        query = QueryDipendente.reuperaCodFiscalePaziente(parametri);
                        break;
                    case "eliminaAppuntamento":
                        query = QueryDipendente.eliminaAppuntamento(parametri);
                        break;
                }
                break;
            case "segreteria":
                switch (parametri.get(1)) {
                    case "visualizzaDatiPersonali":
                        query = QuerySegreteria.viasualizzaDatiPersonali(parametri);
                        break;
                    case "visualizzaPaziente":
                        query = QuerySegreteria.viasualizzaPaziente(parametri);
                        break;
                    case "visualizzaDatiProfessionali":
                        query = QuerySegreteria.visualizzaDatiProfessionali(parametri);
                        break;
                    case "visualizzaDipendenti":
                        query = QuerySegreteria.visualizzaDipendenti();
                        break;
                    case "visualizzaPazienti":
                        query = QuerySegreteria.visualizzaPazienti();
                        break;
                    case "cercaPassword":
                        query = QuerySegreteria.cercaPassword(parametri);
                        break;
                    case "rimuoviRecordEntita":
                        query = QuerySegreteria.rimuoviRecordEntita(parametri);
                        break;
                    case "rimuoviTabellaMedico":
                        query = QuerySegreteria.rimuoviTabellaMedico(parametri);
                        break;
                    case "rimuoviPrenotazione":
                        query = QuerySegreteria.rimuoviPrenotazione(parametri);
                        break;
                    case "aggiungiDipendente":
                        query = QuerySegreteria.aggiungiDipendente(parametri);
                        break;
                    case "creaTabellaMedico":
                        query = QuerySegreteria.creaTabellaMedico(parametri);
                        break;
                    case "inserisciRecordsTabella":
                        query = QuerySegreteria.inserisciRecordsTabella(parametri);
                        break;
                    case "modificaCartellaClinica":
                        query = QuerySegreteria.modificaCartellaClinica(parametri);
                        break;
                    case "modificaDatiPersonali":
                        query = QuerySegreteria.modificaDatiPersonali(parametri);
                        break;
                    case "modificaUsernamePassword":
                        query = QuerySegreteria.modificaUsernamePassword(parametri);
                        break;
                    case "modificaDatiProfessionaliPaziente":
                        query = QuerySegreteria.modificaDatiProfessionaliPaziente(parametri);
                        break;
                    case "modificaDatiProfessionaliDipendente":
                        query = QuerySegreteria.modificaDatiProfessionaliDipendente(parametri);
                        break;
                    case "checkPrenotazioni":
                        query = QuerySegreteria.checkPrenotazioni(parametri);
                        break;
                    case "checkSegreteria":
                        query = QuerySegreteria.checkSegreteria(parametri);
                        break;
                    case "checkDipendente":
                        query = QuerySegreteria.checkDipendente(parametri);
                        break;
                    case "checkPaziente":
                        query = QuerySegreteria.checkPaziente(parametri);
                        break;
                }
                break;
            default:
                System.out.println("Errore parametro0");
                break;
        }
        return query;
    }
}
