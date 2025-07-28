package Progetto.StaticClass;

import Progetto.Body.Roles;
import Progetto.Body.StateMenu;
import Progetto.Body.StateWrapper;
import Progetto.Connection.ConnectionManager;
import Progetto.Entity.Dipendente;
import Progetto.Entity.Persona;
import Progetto.Entity.Utente;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static Progetto.StaticClass.password.generatePassword;

public class MenuCmd {

    private static Scanner scanner = new Scanner(System.in);
    private static String cmd;

    public static void menuPrincipale(StateWrapper currentState){
        while (currentState.stato == StateMenu.NONE) {
            System.out.println("Benvenuto in OSP.");
            System.out.println("1) Accedi\n2) Registrati\n0) Esci");
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    currentState.stato = StateMenu.LOGIN;
                    break;
                case "2":
                    currentState.stato = StateMenu.REGISTRATION;
                    break;
                case "0":
                    currentState.stato = StateMenu.EXIT;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    public static void menuLogin(StateWrapper currentState, ConnectionManager connectionManager) throws IOException, ClassNotFoundException {
        while (currentState.stato == StateMenu.LOGIN) {
            System.out.print("Username:");
            String username = scanner.nextLine();
            System.out.print("Password:");
            String password = scanner.nextLine();
            confermaAnnulla();
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    currentState.codiceFiscale = Parametri.effettuaAccesso(username, password, connectionManager);
                    if (currentState.codiceFiscale == null){
                        System.out.println("Username o password non validi.");
                    }
                    else {
                        System.out.println("Accesso completato.");
                        if (Parametri.checkPaziente(currentState.codiceFiscale, connectionManager) != null) currentState.stato = StateMenu.PATIENT;
                        if (Parametri.checkSegreteria(currentState.codiceFiscale, connectionManager) != null) currentState.stato = StateMenu.SECRETARIAT;
                        if (Parametri.checkPaziente(currentState.codiceFiscale, connectionManager) == null && Parametri.checkSegreteria(currentState.codiceFiscale, connectionManager) == null) currentState.stato = StateMenu.EMPLOYEE;
                    }
                    break;
                case "0":
                    currentState.stato = StateMenu.NONE;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    public static void menuRegistration(StateWrapper currentState, ConnectionManager connectionManager) {
        while (currentState.stato == StateMenu.REGISTRATION) {
            Persona datiPersona = Azione.registrazionePersona();
            confermaAnnulla();
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    if (Check.NomeCognomeCitta(datiPersona.getNome_persona()) &&
                            Check.NomeCognomeCitta(datiPersona.getCognome_persona()) &&
                            Check.NomeCognomeCitta(datiPersona.getCitta_persona()) &&
                            Check.CodiceFiscale(datiPersona.getCodice_fiscale()) &&
                            Check.Email(datiPersona.getEmail_persona()) &&
                            Check.Cellulare(datiPersona.getCellulare_persona()) &&
                            Check.Date(datiPersona.getData_nascita()) &&
                            Parametri.checkCodiceFiscale(datiPersona.getCodice_fiscale(), connectionManager) == null) {
                        boolean menu1 = true;
                        while (menu1) {
                            Utente datiUtente = Azione.registrazioneUtente(datiPersona);
                            System.out.print("Conferma password:");
                            String confermaPassword = scanner.nextLine();
                            confermaAnnulla();
                            cmd = scanner.nextLine();
                            switch (cmd) {
                                case "1":
                                    if (Check.Password(datiUtente.getPassword()) && datiUtente.getPassword().equals(confermaPassword)
                                            && Parametri.checkUsername(datiUtente.getUsername(), connectionManager) == null) {
                                        if (Parametri.inserisciPersona(datiPersona, connectionManager) != null
                                                && Parametri.inserisciUtente(datiUtente, datiPersona.getCodice_fiscale(),connectionManager) != null){
                                            System.out.println("Registrazione completata.");
                                            if (Parametri.inserisciPaziente(datiPersona, connectionManager) != null) {
                                                menu1 = false;
                                                currentState.stato = StateMenu.NONE;
                                            }
                                            else System.out.println("Errore inserimento paziente.");
                                        } else {
                                            System.out.println("Errore nella registrazione.");
                                        }
                                    }
                                    else {
                                        System.out.println("Username esistente o password non valida.\nLa password deve essere lunga almeno 8 caratteri e deve contenere almeno 1 simbolo e un carattere maiuscolo.");
                                    }
                                    break;
                                case "0":
                                    menu1 = false;
                                    break;
                                default:
                                    System.out.println("Opzione non valida.");
                            }
                        }
                    }
                    else {
                        System.out.println("Credenziali non valide.");
                    }
                    break;
                case "0":
                    currentState.stato = StateMenu.NONE;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    public static void menuSecretariat(StateWrapper currentState) {
        while (currentState.stato == StateMenu.SECRETARIAT) {
            menuSegreteria();
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    currentState.stato = StateMenu.DATI_PERSONALI;
                    break;
                case "2":
                    currentState.stato = StateMenu.DATI_PROFESSIONALI;
                    break;
                case "3":
                    currentState.stato = StateMenu.MANAGE_DIPENDENTI;
                    break;
                case "4":
                    currentState.stato = StateMenu.MANAGE_PAZIENTI;
                    break;
                case "5":
                    currentState.stato = StateMenu.NONE;
                    currentState.ruolo = Roles.NONE;
                    currentState.codiceFiscale = null;
                    break;
                case "0":
                    currentState.stato = StateMenu.EXIT;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    public static void menuManageDipendenti(StateWrapper currentState, ConnectionManager connectionManager) {
        while (currentState.stato == StateMenu.MANAGE_DIPENDENTI) {
            System.out.println("Dipendenti");
            Parametri.visualizzaDipendenti(connectionManager);
            cercaAggiungiRimuovi();
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    boolean scelta = true;
                    while (scelta) {
                        System.out.println("Inserire il codice fiscale del dipendente.");
                        String dipendente = scanner.nextLine();
                        if (Parametri.checkDipendente(dipendente, connectionManager) != null) {
                            System.out.println("Dati professionali.");
                            Parametri.visualizzaDatiProfessionali(dipendente, connectionManager);
                            System.out.println();
                            System.out.println("Dati personali.");
                            Parametri.visualizzaDatiPersonali(dipendente, connectionManager);
                            rimuoviIndietro();
                            cmd = scanner.nextLine();
                            switch (cmd) {
                                case "1":
                                    if (Parametri.rimuoviDipendente(dipendente, connectionManager))
                                        System.out.println("Rimozione completata");
                                    else System.out.println("Errore.");
                                    scelta = false;
                                    break;
                                case "0":
                                    scelta = false;
                                    break;
                                default:
                                    System.out.println("Opzione non valida.");
                            }
                        } else {
                            System.out.println("Utente non trovato.");
                            break;
                        }
                    }
                    break;
                case "2":
                    boolean registrazione = true;
                    while (registrazione) {
                        Persona datiPersona = Azione.registrazionePersona();
                        confermaAnnulla();
                        cmd = scanner.nextLine();
                        switch (cmd) {
                            case "1":
                                if (Check.NomeCognomeCitta(datiPersona.getNome_persona()) &&
                                        Check.NomeCognomeCitta(datiPersona.getCognome_persona()) &&
                                        Check.NomeCognomeCitta(datiPersona.getCitta_persona()) &&
                                        Check.CodiceFiscale(datiPersona.getCodice_fiscale()) &&
                                        Check.Email(datiPersona.getEmail_persona()) &&
                                        Check.Cellulare(datiPersona.getCellulare_persona()) &&
                                        Check.Date(datiPersona.getData_nascita()) &&
                                        Parametri.checkCodiceFiscale(datiPersona.getCodice_fiscale(), connectionManager) == null) {
                                    System.out.println("Inserire i campi richiesti.");
                                    System.out.print("Sede ospedaliera:");
                                    String sede = scanner.nextLine();
                                    int stipendio = 0;
                                    boolean inputValido = false;
                                    while (!inputValido) {
                                        System.out.print("Stipendio:");
                                        try {
                                            stipendio = scanner.nextInt();
                                            inputValido = true;
                                            scanner.nextLine();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Errore: l'input non è un numero valido. Riprova.");
                                        }
                                    }
                                    System.out.print("Professione:");
                                    String professione = scanner.nextLine();
                                    LocalDate dataContratto = null;
                                    while (dataContratto == null) {
                                        try {
                                            System.out.print("Data termine del contratto (formato: yyyy-MM-dd):");
                                            String dataNascitaInput = scanner.nextLine();
                                            dataContratto = LocalDate.parse(dataNascitaInput); // Prova a convertire l'input in LocalDate
                                        } catch (DateTimeParseException e) {
                                            System.out.println("Formato data non valido. Riprova utilizzando il formato corretto (yyyy-MM-dd).");
                                        }
                                    }
                                    Utente datiUtente = new Utente(datiPersona.getNome_persona(), datiPersona.getCognome_persona(), datiPersona.getData_nascita(),
                                            datiPersona.getCitta_persona(), datiPersona.getResidenza(), datiPersona.getCodice_fiscale(), datiPersona.getEmail_persona(),
                                            datiPersona.getCellulare_persona(), datiPersona.getCodice_fiscale(), generatePassword(8));
                                    Dipendente datiDipendente = new Dipendente(datiPersona.getNome_persona(), datiPersona.getCognome_persona(), datiPersona.getData_nascita(),
                                            datiPersona.getCitta_persona(), datiPersona.getResidenza(), datiPersona.getCodice_fiscale(), datiPersona.getEmail_persona(),
                                            datiPersona.getCellulare_persona(), datiPersona.getCodice_fiscale(), generatePassword(8), null, sede, stipendio, professione, dataContratto);
                                    if (Parametri.inserisciMedico(datiPersona, datiUtente, datiDipendente, connectionManager)) {
                                        System.out.println("Inserimento completato.");
                                    }
                                    else {
                                        System.out.println("Errore: Inserimento medico.");
                                    }
                                    registrazione = false;
                                }
                                else {
                                    System.out.println("Credenziali non valide.");
                                }
                                break;
                            case "0":
                                currentState.stato = StateMenu.NONE;
                                break;
                            default:
                                System.out.println("Opzione non valida.");
                        }
                    }
                    break;
                case "3":
                    System.out.println("Inserire il codice fiscale del dipendente da rimuovere.");
                    String dipendente = scanner.nextLine();
                    if (Parametri.checkDipendente(dipendente , connectionManager) != null) {
                        if (Parametri.rimuoviDipendente(dipendente ,connectionManager))
                            System.out.println("Rimozione completata");
                        else System.out.println("Errore.");
                    } else {
                        System.out.println("Codice fiscale non valido.");
                    }
                    break;
                case "0":
                    currentState.stato = StateMenu.SECRETARIAT;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    public static void menuManagePazienti(StateWrapper currentState, ConnectionManager connectionManager) {
        while (currentState.stato == StateMenu.MANAGE_PAZIENTI) {
            System.out.println("Pazienti.");
            Parametri.visualizzaPazienti(connectionManager);
            cercaRimuovi();
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    boolean scelta = true;
                    System.out.println("Inserire il codice fiscale del paziente.");
                    String paziente = scanner.nextLine();
                    while (scelta) {
                        if (Parametri.checkPaziente(paziente, connectionManager) != null) {
                            System.out.println("Dati del paziente.");
                            Parametri.visualizzaDatiPaziente(paziente, connectionManager);
                            System.out.println("Dati personali.");
                            Parametri.visualizzaDatiPersonali(paziente, connectionManager);
                            modificaPatologiaRimuoviIndietro();
                            cmd = scanner.nextLine();
                            switch (cmd) {
                                case "1":
                                    Parametri.visualizzaCartellaClinica(paziente, connectionManager);
                                    System.out.println("Scrivere la cartella clinica (copiare ed incollare in caso di necessità):");
                                    String cartella = scanner.nextLine();
                                    if (Parametri.modificaCartellaClinica(paziente, cartella, connectionManager)) {
                                        System.out.println("Modifica effettuata.");
                                    } else {
                                        System.out.println("Errore: Modifica cartella clinica.");
                                    }
                                    break;
                                case "2":
                                    if (Parametri.rimuoviPaziente(paziente, connectionManager)) System.out.println("Rimozione completata.");
                                    else System.out.println("Errore: Rimozione del paziente.");
                                    scelta = false;
                                    break;
                                case "0":
                                    scelta = false;
                                    break;
                                default:
                                    System.out.println("Opzione non valida.");
                            }
                        } else {
                            System.out.println("Utente non trovato.");
                            break;
                        }
                    }
                    break;
                case "2":
                    System.out.println("Inserire codice fiscale del paziente da rimuovere.");
                    String pazienteDue = scanner.nextLine();
                    if (Parametri.checkPaziente(pazienteDue, connectionManager) != null) {
                        if (Parametri.rimuoviPaziente(pazienteDue, connectionManager)) System.out.println("Rimozione completata.");
                        else System.out.println("Errore: Rimozione del paziente.");
                    } else System.out.println("Utente non trovato.");
                    break;
                case "0":
                    currentState.stato = StateMenu.SECRETARIAT;
                    break;
            }
        }
    }

    public static void menuDatiPersonali(StateWrapper currentState, ConnectionManager connectionManager) {
        while (currentState.stato == StateMenu.DATI_PERSONALI) {
            System.out.println("Dati personali:");
            Parametri.visualizzaDatiPersonali(currentState.codiceFiscale, connectionManager);
            modificaIndietro();
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    currentState.stato = StateMenu.MODIFY_DATI_PERSONALI;
                    break;
                case "0":
                    if (currentState.ruolo == Roles.SECRETARIAT) currentState.stato = StateMenu.SECRETARIAT;
                    if (currentState.ruolo == Roles.PATIENT) currentState.stato = StateMenu.PATIENT;
                    if (currentState.ruolo == Roles.EMPLOYEE) currentState.stato = StateMenu.EMPLOYEE;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    public static void menuModificaDatiPersonali(StateWrapper currentState, ConnectionManager connectionManager) {
        while (currentState.stato == StateMenu.MODIFY_DATI_PERSONALI) {
            stampaModificaDatiPersonali();
            String dato = "";
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    System.out.print("Scrivi il nuovo nome: ");
                    dato = scanner.nextLine();
                    if (!Check.NomeCognomeCitta(dato)) System.out.println("Inserimento non valido.");
                    else {
                        if (Parametri.modificaDatiPersonali("nome", dato, currentState.codiceFiscale, connectionManager)) {
                            if (currentState.ruolo == Roles.PATIENT) {
                                if (Parametri.modificaDatiProfessionaliPaziente("nome_paziente", dato, currentState.codiceFiscale, connectionManager))
                                    System.out.println("Modifica completata.");
                                else System.out.println("Errore: Modifica del nome.");
                            } else {
                                if (Parametri.modificaDatiProfessionaliDipendente("nome_medico", dato, currentState.codiceFiscale, connectionManager))
                                    System.out.println("Modifica completata.");
                                else System.out.println("Errore: Modifica del nome.");
                            }
                        } else System.out.println("Errore: Modifica del nome.");
                    }
                    break;
                case "2":
                    System.out.print("Scrivi il nuovo cognome: ");
                    dato = scanner.nextLine();
                    if (!Check.NomeCognomeCitta(dato)) System.out.println("Inserimento non valido.");
                    else {
                        if (Parametri.modificaDatiPersonali("cognome", dato, currentState.codiceFiscale, connectionManager)) {
                            if (currentState.ruolo == Roles.PATIENT) {
                                if (Parametri.modificaDatiProfessionaliPaziente("cognome_paziente", dato, currentState.codiceFiscale, connectionManager))
                                    System.out.println("Modifica completata.");
                                else System.out.println("Errore: Modifica del nome.");
                            } else {
                                if (Parametri.modificaDatiProfessionaliDipendente("cognome_medico", dato, currentState.codiceFiscale, connectionManager))
                                    System.out.println("Modifica completata.");
                                else System.out.println("Errore: Modifica del nome.");
                            }
                        } else System.out.println("Errore: Modifica del nome.");
                    }
                    break;
                case "3":
                    System.out.print("Scrivi la nuova data di nascita (formato: yyyy-MM-dd): ");
                    LocalDate dataNascita = null;
                    while (dataNascita == null) {
                        try {
                            dato = scanner.nextLine();
                            dataNascita = LocalDate.parse(dato);
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato data non valido. Riprova utilizzando il formato corretto (yyyy-MM-dd).");
                        }
                    }
                    if (Check.Date(dataNascita)) {
                        if (Parametri.modificaDatiPersonali("data_nascita", dato,currentState.codiceFiscale, connectionManager)) {
                            System.out.println("Modifica completata.");
                        } else System.out.println("Errore: Modifica della data di nascita.");
                    }
                    else System.out.println("Inserimento non valido.");
                    break;
                case "4":
                    System.out.print("Scrivi la nuova città: ");
                    dato = scanner.nextLine();
                    if (!Check.NomeCognomeCitta(dato)) System.out.println("Inserimento non valido.");
                    else {
                        if (Parametri.modificaDatiPersonali("luogo_nascita", dato,currentState.codiceFiscale, connectionManager)) {
                            System.out.println("Modifica completata.");
                        } else System.out.println("Errore: Modifica del luogo di nascita.");
                    }
                    break;
                case "5":
                    System.out.print("Scrivi la nuova residenza: ");
                    dato = scanner.nextLine();
                    if (Parametri.modificaDatiPersonali("residenza", dato,currentState.codiceFiscale, connectionManager)) {
                        System.out.println("Modifica completata.");
                    } else System.out.println("Errore: Modifica della residenza.");
                    break;
                case "6":
                    System.out.print("Scrivi la nuova email: ");
                    dato = scanner.nextLine();
                    if (!Check.Email(dato)) System.out.println("Inserimento non valido.");
                    else {
                        if (Parametri.modificaDatiPersonali("email", dato,currentState.codiceFiscale, connectionManager)) {
                            System.out.println("Modifica completata.");
                        } else System.out.println("Errore: Modifica email.");
                    }
                    break;
                case "7":
                    System.out.print("Scrivi il nuovo numero di telefono: ");
                    dato = scanner.nextLine();
                    if (!Check.Cellulare(dato)) System.out.println("Inserimento non valido.");
                    else {
                        if (Parametri.modificaDatiPersonali("telefono", dato,currentState.codiceFiscale, connectionManager)) {
                            System.out.println("Modifica completata.");
                        } else System.out.println("Errore: Modifica del numero di telefono.");
                    }
                    break;
                case "0":
                    currentState.stato = StateMenu.DATI_PERSONALI;
                    break;
                default:
                    System.out.println("Opzione non valida.");

            }
        }
    }

    public static void menuDatiProfessionali(StateWrapper currentState, ConnectionManager connectionManager) {
        if (currentState.ruolo == Roles.PATIENT) {
            System.out.println("Dati del paziente:");
            Parametri.visualizzaDatiPaziente(currentState.codiceFiscale, connectionManager);
        }
        else {
            if (currentState.ruolo != Roles.PATIENT) System.out.println("Dati professionali:");
            Parametri.visualizzaDatiProfessionali(currentState.codiceFiscale, connectionManager);
        }
        stampaMenuDatiProfessionali();
        cmd = scanner.nextLine();
        switch (cmd) {
            case "1":
                String username, password;
                System.out.println("Per cambiare l'username e la password è richiesta la password attuale.");
                System.out.print("Password:");
                password = scanner.nextLine();
                if (Parametri.cercaPassword(currentState.codiceFiscale, password,connectionManager )) {
                    while (true) {
                        System.out.print("Nuovo username:");
                        username = scanner.nextLine();
                        System.out.print("Nuova password:");
                        password = scanner.nextLine();
                        System.out.print("Ripeti la nuova password:");
                        String ripetiPassword = scanner.nextLine();
                        if (Parametri.checkUsername(username, connectionManager) == null && Check.Password(password) && password.equals(ripetiPassword)) {
                            if (Parametri.modificaUsernamePassword("username", username, currentState.codiceFiscale, connectionManager)) {
                                if (Parametri.modificaUsernamePassword("password_utente", password, currentState.codiceFiscale, connectionManager)) {
                                    System.out.println("Modifica completata.");
                                } else System.out.println("Errore: Modifica password.");
                            } else System.out.println("Errore: Modifica username.");
                        } else {
                            System.out.println("Username esistente o password non valida.\nLa password deve essere lunga almeno 8 caratteri e deve contenere almeno 1 simbolo e un carattere maiuscolo.");
                        }
                        break;
                    }
                } else System.out.println("Password non valida.");
                break;
            case "0":
                if (currentState.ruolo == Roles.SECRETARIAT) currentState.stato = StateMenu.SECRETARIAT;
                if (currentState.ruolo == Roles.PATIENT) currentState.stato = StateMenu.PATIENT;
                if (currentState.ruolo == Roles.EMPLOYEE) currentState.stato = StateMenu.EMPLOYEE;
                break;
            default:
                System.out.println("Opzione non valida.");
        }
    }

    public static void menuEmployee(StateWrapper currentState) {
        while (currentState.stato == StateMenu.EMPLOYEE) {
            menuMedico();
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    currentState.stato = StateMenu.DATI_PERSONALI;
                    break;
                case "2":
                    currentState.stato = StateMenu.DATI_PROFESSIONALI;
                    break;
                case "3":
                    currentState.stato = StateMenu.APPUNTAMENTI;
                    break;
                case "4":
                    boolean scelta = true;
                    while (scelta) {
                        System.out.println("Email segreteria: emailsegreteria@osp.it");
                        System.out.println("0) Indietro");
                        cmd = scanner.nextLine();
                        switch (cmd) {
                            case "0":
                                scelta = false;
                                break;
                            default:
                                System.out.println("Opzione non valida.");
                        }
                    }
                    break;
                case "5":
                    currentState.stato = StateMenu.NONE;
                    currentState.ruolo = Roles.NONE;
                    currentState.codiceFiscale = null;
                    break;
                case "0":
                    currentState.stato = StateMenu.EXIT;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    public static void menuAppuntamenti(StateWrapper currentState, ConnectionManager connectionManager) {
        while (currentState.stato == StateMenu.APPUNTAMENTI) {
            System.out.println("Appuntamenti:");
            if (Parametri.visualizzaAppuntamenti(currentState.codiceFiscale, connectionManager)) {
                System.out.println("1) Ulteriori dettagli\n0) Indietro");
                cmd = scanner.nextLine();
                switch (cmd) {
                    case "1":
                        int id = 0;
                        boolean inputValido = false;
                        while (!inputValido) {
                            System.out.print("Inserisci ID:");
                            try {
                                id = scanner.nextInt();
                                scanner.nextLine();
                                inputValido = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Errore: l'input non è un numero valido. Riprova.");
                                scanner.next();
                            }
                        }
                        boolean scelta = true;
                        while (scelta) {
                            if (Parametri.visualizzaAppuntamento(id, connectionManager) != null) {
                                System.out.println("1) Visualizza paziente\n2) Termina appuntamento\n0) Indietro");
                                cmd = scanner.nextLine();
                                switch (cmd) {
                                    case "1":
                                        boolean sceltaDue = true;
                                        while (sceltaDue) {
                                            System.out.println("Dati del paziente.");
                                            String paziente = Parametri.recuperaCodFiscalePaziente(id, connectionManager);
                                            Parametri.visualizzaDatiPaziente(paziente, connectionManager);
                                            System.out.println("1) Modifica la cartella clinica\n0) Indietro");
                                            cmd = scanner.nextLine();
                                            switch (cmd) {
                                                case "1":
                                                    Parametri.visualizzaCartellaClinica(paziente, connectionManager);
                                                    System.out.println("Scrivere la cartella clinica (copiare ed incollare in caso di necessità):");
                                                    String cartella = scanner.nextLine();
                                                    if (Parametri.modificaCartellaClinica(paziente, cartella, connectionManager)) {
                                                        System.out.println("Modifica effettuata.");
                                                    } else {
                                                        System.out.println("Errore: Modifica cartella clinica.");
                                                    }
                                                    break;
                                                case "0":
                                                    sceltaDue = false;
                                                    break;
                                                default:
                                                    System.out.println("Opzione non valida.");
                                            }
                                        }
                                        break;
                                    case "2":
                                        if (Parametri.eliminaAppuntamento(id, connectionManager))
                                            System.out.println("Appuntamento terminato.");
                                        else System.out.println("Errore: eliminazione appuntamento.");
                                        scelta = false;
                                        break;
                                    case "0":
                                        scelta = false;
                                        break;
                                    default:
                                        System.out.println("Opzione non valida.");
                                }
                            } else {
                                System.out.println("ID appuntamento non valido.");
                                break;
                            }
                        }
                        break;
                    case "0":
                        currentState.stato = StateMenu.EMPLOYEE;
                        break;
                    default:
                        System.out.println("Opzione non valida.");
                }
            } else {
                System.out.println("Nessun appuntamento previsto.");
                currentState.stato = StateMenu.EMPLOYEE;
            }
        }
    }

    public static void menuPatient(StateWrapper currentState) {
        while (currentState.stato == StateMenu.PATIENT) {
            menuPaziente();
            cmd = scanner.nextLine();
            switch (cmd){
                case "1":
                    currentState.stato = StateMenu.DATI_PERSONALI;
                    break;
                case "2":
                    currentState.stato = StateMenu.DATI_PROFESSIONALI;
                    break;
                case "3":
                    currentState.stato = StateMenu.TICKETS;
                    break;
                case "4":
                    boolean scelta = true;
                    while (scelta) {
                        System.out.println("Email segreteria: emailsegreteria@osp.it");
                        System.out.println("0) Indietro");
                        cmd = scanner.nextLine();
                        switch (cmd) {
                            case "0":
                                scelta = false;
                                break;
                            default:
                                System.out.println("Opzione non valida.");
                        }
                    }
                    break;
                case "5":
                    currentState.stato = StateMenu.NONE;
                    currentState.ruolo = Roles.NONE;
                    currentState.codiceFiscale = null;
                    break;
                case "0":
                    currentState.stato = StateMenu.EXIT;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    public static void menuTickets(StateWrapper currentState, ConnectionManager connectionManager) {
        while (currentState.stato == StateMenu.TICKETS) {
            System.out.println("Appuntamenti:");
            if (!Parametri.visualizzaTickets(currentState.codiceFiscale, connectionManager)) System.out.println("Nessun aapuntamento previsto.");
            System.out.println("1) Prenota un appuntamento\n2) Cancella un appuntamento\n3) Paga\n0) Indietro");
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    boolean scelta = true;
                    while (scelta) {
                        ArrayList<String> ruoli = Parametri.listaRuoli(connectionManager);
                        DynamicSwitch.inizializzaMap(ruoli);
                        System.out.println("Scegli il tipo di visita.");
                        DynamicSwitch.stampaMap();
                        String ruoloScelto = DynamicSwitch.sceltaUtente(scanner.nextLine());
                        if (ruoloScelto == null) System.out.println("Opzione non valida.");
                        else {
                            if (ruoloScelto.equals("Indietro")) break;
                            else {
                                System.out.print("Inserire la data dell'appuntamento (formato: yyyy-MM-dd): ");
                                LocalDate dataAppuntamento = null;
                                String dato ="";
                                while (dataAppuntamento == null) {
                                    try {
                                        dato = scanner.nextLine();
                                        dataAppuntamento = LocalDate.parse(dato);
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Formato data non valido. Riprova utilizzando il formato corretto (yyyy-MM-dd).");
                                    }
                                }
                                String orario = "";
                                String ora ="";
                                boolean sceltaDue = true;
                                while (sceltaDue) {
                                    System.out.println("Scegliere l'orario dell'appuntamento:");
                                    System.out.println("1) 9:00-10:00\n2) 10:00-11:00\n3) 11:00-12:00\n4) 12:00-13:00\n5) 15:00-16:00\n6) 16:00-17:00\n7) 17:00-18:00\n8) 18:00-19:00\n9) 19:00-20:00");
                                    cmd = scanner.nextLine();
                                    switch (cmd) {
                                        case "1":
                                            orario = "ora9";
                                            ora = "09:00:00";
                                            sceltaDue = false;
                                            break;
                                        case "2":
                                            orario = "ora10";
                                            ora = "10:00:00";
                                            sceltaDue = false;
                                            break;
                                        case "3":
                                            orario = "ora11";
                                            ora = "11:00:00";
                                            sceltaDue = false;
                                            break;
                                        case "4":
                                            orario = "ora12";
                                            ora = "12:00:00";
                                            sceltaDue = false;
                                            break;
                                        case "5":
                                            orario = "ora15";
                                            ora = "15:00:00";
                                            sceltaDue = false;
                                            break;
                                        case "6":
                                            orario = "ora16";
                                            ora = "16:00:00";
                                            sceltaDue = false;
                                            break;
                                        case "7":
                                            orario = "ora17";
                                            ora = "17:00:00";
                                            sceltaDue = false;
                                            break;
                                        case "8":
                                            orario = "ora18";
                                            ora = "18:00:00";
                                            sceltaDue = false;
                                            break;
                                        case "9":
                                            orario = "ora19";
                                            ora = "19:00:00";
                                            sceltaDue = false;
                                            break;
                                        default:
                                            System.out.println("Opzione non valida.");
                                    }
                                }
                                ArrayList<String> medici = Parametri.listaMedici(ruoloScelto , connectionManager);
                                String codiceFiscaleMedico = null;
                                for (String medico : medici) {
                                    String esito = Parametri.trovaOrarioDataMedico(orario, medico, dato, connectionManager);
                                    if (esito == null){}
                                    else {
                                        if (!esito.equals("x")) {
                                            codiceFiscaleMedico = medico;
                                            break;
                                        }
                                    }
                                }
                                if (codiceFiscaleMedico == null) System.out.println("Nessun appuntamento disponibile. Scegli un altra data/ora.");
                                else {
                                    ArrayList<String> nomeCognomeMedico = Parametri.nomeMedico(codiceFiscaleMedico, connectionManager);
                                    String nomeCognomePaziente = Parametri.nomePaziente(currentState.codiceFiscale, connectionManager);
                                    System.out.print("Scrivere la tipologia dell'appuntamento: ");
                                    String tipologia = scanner.nextLine();
                                    boolean sceltaTre = true;
                                    while (sceltaTre) {
                                        System.out.println("Appuntamento:");
                                        String stampa = String.format("Nome paziente: %s, Nome medico: %s %s, Data/ora: %s %s, Sede ospedaliera: %s, Tipologia: %s", nomeCognomePaziente, nomeCognomeMedico.get(0), nomeCognomeMedico.get(1), dato, ora, nomeCognomeMedico.get(2), tipologia);
                                        System.out.println(stampa);
                                        System.out.println("1) Conferma\n0) Annulla");
                                        cmd = scanner.nextLine();
                                        switch (cmd) {
                                            case "1":
                                                if (!Parametri.contrassegnaMedico(codiceFiscaleMedico, orario, dato, connectionManager)) {
                                                    System.out.println("Errore: Contrassegno medico.");
                                                } else {
                                                    String dataOra = dato + " " + ora;
                                                    if(!Parametri.aggiungiPrenotazione(currentState.codiceFiscale, codiceFiscaleMedico, dataOra, nomeCognomeMedico.get(2), tipologia, "80", connectionManager)) System.out.println("Errore: Aggiunta prenotazione.");
                                                    else {
                                                        System.out.println("Inserimento completato.");
                                                        scelta = false;
                                                        sceltaTre = false;
                                                    }
                                                }
                                                break;
                                            case "0":
                                                sceltaTre = false;
                                                scelta = false;
                                                break;
                                            default:
                                                System.out.println("Opzione non valida.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "2":
                    int id = 0;
                    boolean inputValido = false;
                    while (!inputValido) {
                        System.out.print("Inserisci ID:");
                        try {
                            id = scanner.nextInt();
                            scanner.nextLine();
                            inputValido = true;
                        } catch (InputMismatchException e) {
                            System.out.println("Errore: l'input non è un numero valido. Riprova.");
                            scanner.next();
                        }
                    }
                    if (Parametri.checkAppuntamento(id, connectionManager)) System.out.println("Errore: Appuntamento non trovato.");
                    else {
                        String orario = "";
                        ArrayList<String> parametri =  Parametri.recuperaParametriContrassegno(id, connectionManager);
                        switch (parametri.get(2)) {
                            case "09:00:00":
                                orario = "ora9";
                                break;
                            case "10:00:00":
                                orario = "ora10";
                                break;
                            case "11:00:00":
                                orario = "ora11";
                                break;
                            case "12:00:00":
                                orario = "ora12";
                                break;
                            case "15:00:00":
                                orario = "ora15";
                                break;
                            case "16:00:00":
                                orario = "ora16";
                                break;
                            case "17:00:00":
                                orario = "ora17";
                                break;
                            case "18:00:00":
                                orario = "ora18";
                                break;
                            case "19:00:00":
                                orario = "ora19";
                                break;
                            default:
                                System.out.println("Opzione non valida.");
                        }
                        if (!Parametri.togliContrassegno(parametri.get(0), orario, parametri.get(1), connectionManager))
                            System.out.println("Errore: Elimina contrassegno.");
                        else{
                            if(!Parametri.eliminaAppuntamento(id, connectionManager)) System.out.println("Errore: Eliminazione appuntamento.");
                            else System.out.println("Appuntamento eliminato.");
                        }
                    }
                    break;
                case "3":
                    int idPagamento = 0;
                    boolean inputValidoDue = false;
                    while (!inputValidoDue) {
                        System.out.print("Inserisci ID della prenotazione da pagare:");
                        try {
                            idPagamento = scanner.nextInt();
                            scanner.nextLine();
                            inputValidoDue = true;
                        } catch (InputMismatchException e) {
                            System.out.println("Errore: l'input non è un numero valido. Riprova.");
                            scanner.next();
                        }
                    }
                    if (Parametri.checkAppuntamento(idPagamento, connectionManager)) System.out.println("Errore: Appuntamento non trovato.");
                    else {
                        if (Parametri.statoPagamento(idPagamento, connectionManager)) System.out.println("Prenotazione già pagata.");
                        else {
                            if(!Parametri.aggiornamentoStatoPagamento(idPagamento, connectionManager))
                                System.out.println("Errore: Aggiornamento stato del pagamento");
                            else System.out.println("Pagamento effettuato.");
                        }
                    }
                    break;
                case "0":
                    currentState.stato = StateMenu.PATIENT;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    public static void rimuoviIndietro(){
        System.out.println("1) Rimuovi\n0) Indietro");
    }

    public static void modificaPatologiaRimuoviIndietro() {
        System.out.println("1) Modifica cartella clinica\n2) Rimuovi\n0) Indietro");
    }

    public static void cercaAggiungiRimuovi(){
        System.out.println("1) Cerca\n2) Aggiungi\n3) Rimuovi\n0) Indietro");
    }

    public static void cercaRimuovi(){
        System.out.println("1) Cerca\n2) Rimuovi\n0) Indietro");
    }

    public static void confermaAnnulla(){
        System.out.println("1) Conferma\n0) Annulla");
    }

    public static void modificaIndietro() {
        System.out.println("1) Modifica\n0) Indietro");
    }

    public static void stampaMenuDatiProfessionali() {
        System.out.println("1) Modifica username e password\n0) Indietro");
    }

    public static void stampaModificaDatiPersonali() {
        System.out.println("Scegli il campo da modificare.");
        System.out.println("1) Nome\n2) Cognome\n3) Data di nascita\n4) Città\n5) Residenza\n6) Email\n7) Numero di telefono\n0) Indietro");
    }

    public static void menuSegreteria() {
        System.out.println("1) Dati personali\n2) Dati professionali\n3) Dipendenti\n4) Pazienti\n5) Logout\n0) Esci");
    }

    public  static void menuMedico() {
        System.out.println("1) Dati personali\n2) Dati professionali\n3) Appuntamenti\n4) Segreteria\n5) Logout\n0) Esci");
    }

    public  static void menuPaziente() {
        System.out.println("1) Dati personali\n2) Dati paziente\n3) Tickets\n4) Segreteria\n5) Logout\n0) Esci");
    }
}
