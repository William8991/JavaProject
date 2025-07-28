package Progetto.Body;

import Progetto.Connection.ConnectionManager;
import Progetto.StaticClass.MenuCmd;

import java.io.IOException;

public class Body {

    public static void body(StateWrapper currentState, ConnectionManager connectionManager) throws IOException, ClassNotFoundException, InterruptedException, RuntimeException {
        while (currentState.stato != StateMenu.EXIT) {
            switch (currentState.stato) {
                case NONE:
                    MenuCmd.menuPrincipale(currentState);
                    break;
                case LOGIN:
                    MenuCmd.menuLogin(currentState, connectionManager);
                case REGISTRATION:
                    MenuCmd.menuRegistration(currentState, connectionManager);
                    break;
                case SECRETARIAT:
                    currentState.ruolo = Roles.SECRETARIAT;
                    MenuCmd.menuSecretariat(currentState);
                    break;
                case EMPLOYEE:
                    currentState.ruolo = Roles.EMPLOYEE;
                    MenuCmd.menuEmployee(currentState);
                    break;
                case PATIENT:
                    currentState.ruolo = Roles.PATIENT;
                    MenuCmd.menuPatient(currentState);
                    break;
                case DATI_PERSONALI:
                    MenuCmd.menuDatiPersonali(currentState, connectionManager);
                    break;
                case MANAGE_DIPENDENTI:
                    MenuCmd.menuManageDipendenti(currentState, connectionManager);
                    break;
                case MANAGE_PAZIENTI:
                    MenuCmd.menuManagePazienti(currentState, connectionManager);
                    break;
                case MODIFY_DATI_PERSONALI:
                    MenuCmd.menuModificaDatiPersonali(currentState, connectionManager);
                    break;
                case DATI_PROFESSIONALI:
                    MenuCmd.menuDatiProfessionali(currentState, connectionManager);
                    break;
                case APPUNTAMENTI:
                    MenuCmd.menuAppuntamenti(currentState, connectionManager);
                    break;
                case TICKETS:
                    MenuCmd.menuTickets(currentState, connectionManager);
                    break;
            }
        }
    }
}
