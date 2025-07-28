package Progetto.Body;

import Progetto.Connection.ConnectionManager;

import java.io.IOException;

public class PreBody implements Runnable{

    private StateWrapper currentState = new StateWrapper(StateMenu.NONE, null, Roles.NONE);


    /* Il Thread PreBody fa partire il Thread per la connessione e gestisce lo stato. */
    public void run(){
        ConnectionManager connectionManager = new ConnectionManager();
        Thread connectionThread = new Thread(connectionManager);
        connectionThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (currentState.stato != StateMenu.EXIT) {
            if (connectionManager.isConnected()) {
                try {
                    Body.body(currentState, connectionManager);
                    System.out.println("Arrivederci");
                    connectionThread.interrupt();
                    connectionThread.join();
                } catch (IOException | ClassNotFoundException | InterruptedException | RuntimeException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    //System.out.println("Errore di comunicazione.");
                }
            } else {
                System.out.println("In attesa di riconnessione...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
