package Progetto.StaticClass;

import Progetto.Connection.ConnectionManager;
import Progetto.Entity.Dipendente;
import Progetto.Entity.Persona;
import Progetto.Entity.Utente;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Parametri {
    public static String effettuaAccesso(String username, String password, ConnectionManager connectionManager) throws IOException, ClassNotFoundException {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "cercaUsernamePassword", username, password));
        connectionManager.getInOutStream().send(parametri);
        Object result = connectionManager.getInOutStream().receive();
        if (result == null) return null;
        if (result instanceof List) {
            List<Map<String, Object>> listOfMap = (List<Map<String, Object>>) result;
            if (listOfMap == null || listOfMap.isEmpty()) {
                System.out.println("La lista è vuota o null.");
                return null;
            }
            String codiceFiscale = "";
            for (Map<String, Object> map : listOfMap) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    codiceFiscale = (String) entry.getValue(); // O il valore, dipende dalla tua logica
                }
            }
            return codiceFiscale;
        } else {
            System.out.println("Tipo di dato ricevuto non è una lista.");
            return null;
        }
    }



    public static String inserisciPersona(Persona dati, ConnectionManager connectionManager) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "aggiungiPersona", dati.getCodice_fiscale(), dati.getNome_persona(), dati.getCognome_persona(), dati.getData_nascita().format(formatter), dati.getCitta_persona(), dati.getResidenza(), dati.getEmail_persona(), dati.getCellulare_persona()));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            else return "Inserimento persona completato.";
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static String inserisciUtente(Utente dati, String codiceFiscale,ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "aggiungiUtente", codiceFiscale,dati.getUsername(), dati.getPassword()));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            else return "Inserimento utente completato.";
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static String inserisciPaziente(Persona datiPersona, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "aggiungiPaziente", datiPersona.getCodice_fiscale(), datiPersona.getNome_persona(), datiPersona.getCognome_persona()));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            else return "Inserimento paziente completato.";
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static String inserisciDipendente(Dipendente dipendente, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "aggiungiDipendente", dipendente.getCodice_fiscale(), dipendente.getNome_persona(), dipendente.getCognome_persona(), Integer.toString(dipendente.getStripendio_dipendente()), dipendente.getSede_ospedale(), dipendente.getProfessione(), dipendente.getTermine_contratto().toString()));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            else return "Inserimento dipendente completato.";
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static String creaTabellaMedico(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "creaTabellaMedico", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            else return "Creazione tabella completato.";
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String inserisciRecordsTabella(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "inserisciRecordsTabella", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            else return "Creazione tabella completato.";
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean inserisciMedico(Persona persona, Utente utente, Dipendente dipendente, ConnectionManager connectionManager) {
        int num = 0;
        if (inserisciPersona(persona, connectionManager) != null) num += 1;
        if (inserisciUtente(utente, utente.getCodice_fiscale(), connectionManager) != null) num += 1;
        if (inserisciDipendente(dipendente, connectionManager) != null) num += 1;
        if (dipendente.getProfessione().equals("Segreteria")) return num == 3;
        else {
            if (creaTabellaMedico(dipendente.getCodice_fiscale(), connectionManager) == null) num += 1;
            if (inserisciRecordsTabella(dipendente.getCodice_fiscale(), connectionManager) != null) num +=1;
            return num == 5;
        }
    }

    public static boolean aggiungiPrenotazione(String codiceFiscalePaziente, String codiceFiscaleMedico, String dataOra, String sede, String tipo, String costo, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "aggiungiPrenotazione", codiceFiscalePaziente, codiceFiscaleMedico, dataOra, sede, tipo, costo));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean contrassegnaMedico(String codiceFiscale, String ora, String data, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "contrassegnaMedico", codiceFiscale, ora, data));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean rimuoviRecordEntita(String codiceFiscale, String tabella, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "rimuoviRecordEntita", tabella, codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean rimuoviPrenotazione(String codiceFiscale, String tabella, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "rimuoviPrenotazione", tabella, codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean rimuoviTabellaMedico(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "rimuoviTabellaMedico", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean rimuoviDipendente(String codiceFiscale, ConnectionManager connectionManager) {
        int num = 0;
        if (!rimuoviTabellaMedico(codiceFiscale, connectionManager)) num += 1;
        if (checkPrenotazioni(codiceFiscale, connectionManager)) {
            if (rimuoviPrenotazione(codiceFiscale, "prenotazioni", connectionManager)) num += 1;
        }
        if (rimuoviRecordEntita(codiceFiscale, "utente", connectionManager)) num += 1;
        if (rimuoviRecordEntita(codiceFiscale, "dipendente", connectionManager)) num += 1;
        if (rimuoviRecordEntita(codiceFiscale, "persona", connectionManager)) num += 1;
        return num == 5 || num == 4;
    }

    public static boolean rimuoviPaziente(String codiceFiscale, ConnectionManager connectionManager) {
        int num = 0;
        if (checkPrenotazioni(codiceFiscale, connectionManager)) {
            if (rimuoviPrenotazione(codiceFiscale, "prenotazioni", connectionManager)) num += 1;
        }
        if (rimuoviRecordEntita(codiceFiscale, "utente", connectionManager)) num += 1;
        if (rimuoviRecordEntita(codiceFiscale, "paziente", connectionManager)) num += 1;
        if (rimuoviRecordEntita(codiceFiscale, "persona", connectionManager)) num += 1;
        return num == 3 || num == 4;
    }

    public static boolean eliminaAppuntamento(int id, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("dipendente", "eliminaAppuntamento", Integer.toString(id)));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean modificaCartellaClinica(String codiceFiscale, String cartella, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "modificaCartellaClinica", codiceFiscale, cartella));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean modificaDatiPersonali(String colonna, String nuovoDato,String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "modificaDatiPersonali", colonna, nuovoDato,codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean modificaDatiProfessionaliPaziente(String colonna, String nuovoDato,String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "modificaDatiProfessionaliPaziente", colonna, nuovoDato,codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean modificaDatiProfessionaliDipendente(String colonna, String nuovoDato,String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "modificaDatiProfessionaliDipendente", colonna, nuovoDato,codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean modificaUsernamePassword(String colonna, String nuovoDato,String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "modificaUsernamePassword", colonna, nuovoDato,codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public  static void visualizzaDatiPersonali(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "visualizzaDatiPersonali", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) System.out.println("Questo utente non esiste.");
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) System.out.println("Questo utente non esiste.");
            for (Map<String, Object> map : listResult) {
                String codFiscale = (String) map.get("codice_fiscale"); // Nome originale della colonna
                String nome = (String) map.get("nome");
                String cognome = (String) map.get("cognome");
                Date dataNascita = (Date) map.get("data_nascita");
                String citta = (String) map.get("luogo_nascita");
                String residenza = (String) map.get("residenza");
                String email = (String) map.get("email");
                String cellulare = (String) map.get("telefono");
                System.out.print("Codice fiscale: " + codFiscale);
                System.out.print("Nome: " + nome);
                System.out.print("Cognome: " + cognome);
                System.out.print("Data di nascita: " + dataNascita);
                System.out.print("Città: " + citta);
                System.out.print("Residenza: " + residenza);
                System.out.print("Email: " + email);
                System.out.println("Numero di telefono: " + cellulare);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void visualizzaDatiProfessionali(String codiceFiscale ,ConnectionManager connectionManager ) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "visualizzaDatiProfessionali", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) System.out.println("Questo utente non esiste.");
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) System.out.println("Questo utente non esiste.");
            for (Map<String, Object> map : listResult) {
                String codFiscale = (String) map.get("codice_fiscale");
                String nome = (String) map.get("nome_medico");
                String cognome = (String) map.get("cognome_medico");
                String email = (String) map.get("email_ospedaliera");
                int stipendio = (int) map.get("stipendio");
                String sede = (String) map.get("sede_ospedale");
                String professione = (String) map.get("professione_medico");
                Date dataContratto = (Date) map.get("data_termine_contratto");
                System.out.print("Codice fiscale: " + codFiscale);
                System.out.print("Nome: " + nome);
                System.out.print("Cognome: " + cognome);
                System.out.print("Email: " + email);
                System.out.print("Stipendio: "+ stipendio);
                System.out.print("Sede ospedaliera: " + sede);
                System.out.print("Professione: " + professione);
                System.out.println("Data termine contratto: " + dataContratto);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void visualizzaDipendenti(ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "visualizzaDipendenti"));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) System.out.println("Questo utente non esiste.");
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) System.out.println("Questo utente non esiste.");
            for (Map<String, Object> map : listResult) {
                String codFiscale = (String) map.get("codice_fiscale");
                String nome = (String) map.get("nome_medico");
                String cognome = (String) map.get("cognome_medico");
                String email = (String) map.get("email_ospedaliera");
                int stipendio = (int) map.get("stipendio");
                String sede = (String) map.get("sede_ospedale");
                String professione = (String) map.get("professione_medico");
                Date dataContratto = (Date) map.get("data_termine_contratto");
                System.out.print("Codice fiscale: " + codFiscale);
                System.out.print(", Nome: " + nome);
                System.out.print(", Cognome: " + cognome);
                System.out.print(", Email: " + email);
                System.out.print(", Stipendio: "+ stipendio);
                System.out.print(", Sede ospedaliera: " + sede);
                System.out.print(", Professione: " + professione);
                System.out.println(", Data termine contratto: " + dataContratto);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void visualizzaPazienti(ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "visualizzaPazienti"));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) System.out.println("Questo utente non esiste.");
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) System.out.println("Questo utente non esiste.");
            for (Map<String, Object> map : listResult) {
                String codFiscale = (String) map.get("codice_fiscale");
                String nome = (String) map.get("nome_paziente");
                String cognome = (String) map.get("cognome_paziente");
                String cartellaClinica = (String) map.get("cartella_clinica");
                System.out.print("Codice fiscale: " + codFiscale);
                System.out.print(", Nome: " + nome);
                System.out.print(", Cognome: " + cognome);
                System.out.println(", Cartella clinica: " + cartellaClinica);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void visualizzaDatiPaziente(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "visualizzaPaziente", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) System.out.println("Questo utente non esiste.");
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) System.out.println("Questo utente non esiste.");
            for (Map<String, Object> map : listResult) {
                String codFiscale = (String) map.get("codice_fiscale");
                String nome = (String) map.get("nome_paziente");
                String cognome = (String) map.get("cognome_paziente");
                String cartellaClinica = (String) map.get("cartella_clinica");
                System.out.print("Codice fiscale: " + codFiscale);
                System.out.print(", Nome: " + nome);
                System.out.print(", Cognome: " + cognome);
                System.out.println(", Cartella clinica: " + cartellaClinica);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void visualizzaCartellaClinica(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "visualizzaPaziente", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) System.out.println("Questo utente non esiste.");
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) System.out.println("Questo utente non esiste.");
            for (Map<String, Object> map : listResult) {
                String cartellaClinica = (String) map.get("cartella_clinica");
                System.out.println("Cartella clinica: " + cartellaClinica);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean visualizzaTickets(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "visualizzaTickets", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return false;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return false;
            for (Map<String, Object> map : listResult) {
                int idPrenotazione = (int) map.get("id_prenotazione");
                String nomeMedico = (String) map.get("nome_medico");
                String cognomeMedico = (String) map.get("cognome_medico");
                String nomePaziente = (String) map.get("nome_paziente");
                String cognomePaziente = (String) map.get("cognome_paziente");
                String professioneMedico = (String) map.get("professione_medico");
                LocalDateTime dataVisita = (LocalDateTime) map.get("data_ora_visita");
                String sede = (String) map.get("sede_ospedale");
                String tipo = (String) map.get("tipo_visita");
                int costo = (int) map.get("costo_visita");
                String statoPagamento = (String) map.get("stato_pagamento");
                System.out.print("ID: " + idPrenotazione);
                System.out.print(", Paziente: " + nomePaziente + " " + cognomePaziente);
                System.out.print(", Medico: " + nomeMedico + " " + cognomeMedico);
                System.out.print(", Professione medico: " + professioneMedico);
                System.out.print(", Data/ora: " + dataVisita);
                System.out.print(", Sede ospedaliera: " + sede);
                System.out.print(", Tipologia: " + tipo);
                System.out.print(", Prezzo: " + costo);
                System.out.println(", Stato pagamento: " + statoPagamento);
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean visualizzaAppuntamenti(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("dipendente", "visualizzaAppuntamenti", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return false;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return false;
            for (Map<String, Object> map : listResult) {
                int idPrenotazione = (int) map.get("id_prenotazione");
                String nomeMedico = (String) map.get("nome_medico");
                String cognomeMedico = (String) map.get("cognome_medico");
                String nomePaziente = (String) map.get("nome_paziente");
                String cognomePaziente = (String) map.get("cognome_paziente");
                String professioneMedico = (String) map.get("professione_medico");
                LocalDateTime dataVisita = (LocalDateTime) map.get("data_ora_visita");
                String sede = (String) map.get("sede_ospedale");
                String tipo = (String) map.get("tipo_visita");
                int costo = (int) map.get("costo_visita");
                String statoPagamento = (String) map.get("stato_pagamento");
                System.out.print("ID: " + idPrenotazione);
                System.out.print(", Paziente: " + nomePaziente + " " + cognomePaziente);
                System.out.print(", Medico: " + nomeMedico + " " + cognomeMedico);
                System.out.print(", Professione medico: " + professioneMedico);
                System.out.print(", Data/ora: " + dataVisita);
                System.out.print(", Sede ospedaliera: " + sede);
                System.out.print(", Tipologia: " + tipo);
                System.out.print(", Prezzo: " + costo);
                System.out.println(", Stato pagamento: " + statoPagamento);
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String visualizzaAppuntamento(int id, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("dipendente", "visualizzaAppuntamento", Integer.toString(id)));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            for (Map<String, Object> map : listResult) {
                int idPrenotazione = (int) map.get("id_prenotazione");
                String nomeMedico = (String) map.get("nome_medico");
                String cognomeMedico = (String) map.get("cognome_medico");
                String nomePaziente = (String) map.get("nome_paziente");
                String cognomePaziente = (String) map.get("cognome_paziente");
                String professioneMedico = (String) map.get("professione_medico");
                LocalDateTime dataVisita = (LocalDateTime) map.get("data_ora_visita");
                String sede = (String) map.get("sede_ospedale");
                String tipo = (String) map.get("tipo_visita");
                int costo = (int) map.get("costo_visita");
                String statoPagamento = (String) map.get("stato_pagamento");
                System.out.print("ID: " + idPrenotazione);
                System.out.print(", Paziente: " + nomePaziente + " " + cognomePaziente);
                System.out.print(", Medico: " + nomeMedico + " " + cognomeMedico);
                System.out.print(", Professione medico: " + professioneMedico);
                System.out.print(", Data/ora: " + dataVisita);
                System.out.print(", Sede ospedaliera: " + sede);
                System.out.print(", Tipologia: " + tipo);
                System.out.print(", Prezzo: " + costo);
                System.out.println(", Stato pagamento: " + statoPagamento);
            }
            return "Completato";
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String recuperaCodFiscalePaziente(int id, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("dipendente", "recuperaCodFiscalePaziente", Integer.toString(id)));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            String codFiscale="";
            for (Map<String, Object> map : listResult) {
                codFiscale = (String) map.get("codice_fiscale_paziente");
            }
            return codFiscale;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> listaRuoli(ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "listaRuoli"));
        ArrayList<String> ruoli = new ArrayList<>();
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            for (Map<String, Object> map : listResult) {
                ruoli.add((String) map.get("professione_medico"));

            }
            return ruoli;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> listaMedici(String ruolo, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "listaMedici", ruolo));
        ArrayList<String> medici = new ArrayList<>();
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            for (Map<String, Object> map : listResult) {
                medici.add((String) map.get("codice_fiscale"));
            }
            return medici;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String trovaOrarioDataMedico(String orario, String codiceFiscale, String data, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "trovaOrarioDataMedico", orario, codiceFiscale, data));
        String esito = null;
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            for (Map<String, Object> map : listResult) {
                esito = (String) map.get(orario);
            }
            return esito;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> nomeMedico (String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "nomeMedico", codiceFiscale));
        ArrayList<String> nomeCognomeSede = new ArrayList<>();
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            for (Map<String, Object> map : listResult) {
                String nome = (String) map.get("nome_medico");
                String cognome = (String) map.get("cognome_medico");
                String sede = (String) map.get("sede_ospedale");
                nomeCognomeSede.add(nome);
                nomeCognomeSede.add(cognome);
                nomeCognomeSede.add(sede);
            }
            return nomeCognomeSede;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String nomePaziente (String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "nomePaziente", codiceFiscale));
        String nomeCognome = null;
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            for (Map<String, Object> map : listResult) {
                String nome = (String) map.get("nome_paziente");
                String cognome = (String) map.get("cognome_paziente");
                nomeCognome = nome + " " + cognome;
            }
            return nomeCognome;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> recuperaParametriContrassegno (int id, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "recuperaParametriContrassegno", Integer.toString(id)));
        ArrayList<String> nomeCognomeSede = new ArrayList<>();
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            for (Map<String, Object> map : listResult) {
                String codiceFiscale = (String) map.get("codice_fiscale_dipendente");
                String data = map.get("DATE(data_ora_visita)").toString();
                String ora = map.get("TIME(data_ora_visita)").toString();
                nomeCognomeSede.add(codiceFiscale);
                nomeCognomeSede.add(data);
                nomeCognomeSede.add(ora);
            }
            return nomeCognomeSede;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean togliContrassegno(String codiceFiscale, String data,String ora, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "togliContrassegno", codiceFiscale, data,ora));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean statoPagamento(int id, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "statoPagamento", Integer.toString(id)));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return false;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return false;
            for (Map<String, Object> map : listResult) {
                return map.get("stato_pagamento").equals("pagato");
            }
            return false;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean aggiornamentoStatoPagamento(int id, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "aggiornamentoStatoPagamento", Integer.toString(id)));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkPrenotazioni(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "checkPrenotazioni", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkAppuntamento(int id , ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "checkAppuntamento", Integer.toString(id)));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result == null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public  static String checkPaziente(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "checkPaziente", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            Map<String, Object> singleMap = listResult.getFirst();
            return (String) singleMap.get("codice_fiscale");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public  static String checkSegreteria(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "checkSegreteria", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            Map<String, Object> singleMap = listResult.getFirst();
            return (String) singleMap.get("codice_fiscale");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public  static String checkDipendente(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "checkDipendente", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            ArrayList<Map<String, Object>> listResult = (ArrayList<Map<String, Object>>) result;
            if (listResult.isEmpty()) return null;
            Map<String, Object> singleMap = listResult.getFirst();
            return (String) singleMap.get("codice_fiscale");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static String checkCodiceFiscale(String codiceFiscale, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "visualizzaDatiPersonali", codiceFiscale));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            Map<String, Object> singleMap = (Map<String, Object>) result;
            return (String) singleMap.get("codice_fiscale");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static String checkUsername(String username, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("paziente", "cercaUsername", username));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            if (result == null) return null;
            Map<String, Object> singleMap = (Map<String, Object>) result;
            return (String) singleMap.get("codice_fiscale");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cercaPassword(String codiceFiscale, String password, ConnectionManager connectionManager) {
        ArrayList<String> parametri = new ArrayList<>(Arrays.asList("segreteria", "cercaPassword", codiceFiscale, password));
        try {
            connectionManager.getInOutStream().send(parametri);
            Object result = connectionManager.getInOutStream().receive();
            return result != null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
