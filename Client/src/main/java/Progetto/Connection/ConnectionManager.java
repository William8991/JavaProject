package Progetto.Connection;

import Progetto.XMLConfig.Parser.XMLManager;
import Progetto.XMLConfig.XMLData.ClientConfig;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionManager implements Runnable{
    private static final String CLIENTCONFIG_FILENAME = "src/main/resources/ClientConfig.xml";
    static String[] serverData = new XMLManager(CLIENTCONFIG_FILENAME, new ClientConfig()).parse();
    private static final int port = Integer.parseInt(serverData[0]);
    private static final String host = serverData[1];
    private Socket socket;
    private boolean isConnected;
    private InOutManage inOutStream;
    //private BlockingQueue<Object> messageQueue;

    public ConnectionManager() {
        this.isConnected = false;
        //this.messageQueue = new LinkedBlockingQueue<>();
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (!isConnected) {
                    connectedToServer();
                }
                monitorConnection();
            } catch (IOException e) {
                System.out.println("Connessione persa. Tentativo di riconnessione...");
                retryConnection();
            }
        }
    }

    public void connectedToServer() throws IOException {
        socket = new Socket(host, port);
        socket.setSoTimeout(2000);
        inOutStream = new InOutManage(new ObjectOutputStream(socket.getOutputStream()) , new ObjectInputStream(socket.getInputStream()));
        isConnected = true;
        System.out.println("Connesso al server.");
    }

    public void monitorConnection() throws IOException {
        while (isConnected) {
            try {
                if(Thread.currentThread().isInterrupted()) return;
                // Prova a inviare un pacchetto vuoto per verificare la connessione
                socket.sendUrgentData(0xFF);  // Invia un byte "urgente" senza dati reali

                Thread.sleep(2000);  // Attende 5 secondi tra un controllo e l'altro
            } catch (IOException e) {
                isConnected = false;
                System.out.println("Connessione persa durante il monitoraggio.");
                throw new IOException("Connessione persa.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

//    public void startReadingThread() {
//        Thread readThread = new Thread(new ReadThread(inOutStream, messageQueue, this));
//        readThread.start();
//    }

    public void retryConnection() {
        while (!isConnected) {
            try {
                Thread.sleep(2000);
                connectedToServer();
            } catch (InterruptedException | IOException e) {
                System.out.println("Tentativo di riconnessione fallito. Riprovo a connettermi...");
            }
        }
    }

//    public Object getMessageFromQueue() throws InterruptedException {
//        return messageQueue.take();  // Estrae l'elemento dalla coda, attende se vuota
//    }

    public InOutManage getInOutStream() {
        return inOutStream;
    }

    public boolean isConnected() { return isConnected; }

    public synchronized void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
