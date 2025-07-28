package Server;

import Routine.RoutineDatabase;
import XMLConfig.Parser.XMLManager;
import XMLConfig.XMLData.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

public class Server {
    private static final int BUFFER_SIZE = 1000;
    // Coda bloccante che funge da buffer per le query dei client, con una dimensione massima di BUFFER_SIZE
    private static final BlockingQueue<QueryWithClient> queryBuffer = new LinkedBlockingQueue<>(BUFFER_SIZE);
    /* Eseguo l'estrazione dei dati per avviare il server e inizializzare in numero massimo di connessioni. */
    /* Inserisco in una stringa il percorso del file XML. */
    private static final String SERVERCONFIG_FILENAME = "src/main/resources/serverConfig.xml";
    /* Faccio un oggetto di tipo XMLManager passando il percorso e l'oggetto contenente le caratteristiche del file XML.
     * Viene eseguito il parsing per ricavare un array di stringhe contenete la porta e il numero di connessioni. */
    static String[] serverData = new XMLManager(SERVERCONFIG_FILENAME, new ServerConfig()).parse();
    private static final int port = Integer.parseInt(serverData[0]);
    private static final int MaxConnections = Integer.parseInt(serverData[1]);

    // Pool di thread per eseguire le query dal buffer, con 5 thread che eseguono le query simultaneamente
    private static final ExecutorService queryExecutor = Executors.newFixedThreadPool(30);
    // Pool di thread per gestire le connessioni dei client, con un massimo di MAX_CLIENTS thread
    private static final ExecutorService clientPool = Executors.newFixedThreadPool(MaxConnections);


    private final RoutineDatabase rt= new RoutineDatabase();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2); // Due thread per due task
     // Definire la prima funzione che vuoi eseguire ogni ora
    private final Runnable routineTabelle = new Runnable() {
        @Override
        public void run() {
            System.out.println("Routine tabelle eseguita all'ora corrente: " + java.time.LocalDateTime.now());
            rt.routineAggiornamentoOrari();
        }
    };
    // Definire la seconda funzione che vuoi eseguire ogni ora
    private final Runnable routinePrenotazioni = new Runnable() {
        @Override
        public void run() {
            System.out.println("Routine prenotazioni eseguita all'ora corrente: " + java.time.LocalDateTime.now());
            rt.routineAggiornamentoPrenotazioni();
        }
    };

    private ServerSocket serverSocket;
    private volatile boolean isRunning = true;

    public void start(){
        /* All'interno del try avvio il server in quella porta. */
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server in esecuzione...");

            // Avvia un thread che ascolta i comandi da console
            Thread consoleListener = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String command = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(command.trim())) {
                        shutdownServer();
                        break;
                    }
                }
                scanner.close();
            });
            consoleListener.start();

             //initial delay è 1 perchè deve partire ogni ora
            scheduler.scheduleAtFixedRate(routineTabelle, 1, 1, TimeUnit.HOURS);
            // Pianificare l'esecuzione della seconda funzione ogni ora
            scheduler.scheduleAtFixedRate(routinePrenotazioni, 1, 1, TimeUnit.HOURS);

            /* Attivo il thread (figlio) per il consumatore passo il buffer e il pool per le esecuzioni. */
            new Thread(new Consumatore(queryBuffer, queryExecutor)).start();

            /* Ciclo che si mette in ascolto del client. */
            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    if (isRunning) { // Controllo aggiuntivo per evitare nuove accettazioni dopo la chiusura
                        System.out.println(clientSocket.getLocalAddress() + " connesso.");
                        clientPool.submit(new handleClient(clientSocket, queryBuffer));
                    } else {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    if (isRunning) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shutdownServer() {
        try {
            System.out.println("Arresto del server in corso...");
            isRunning = false;

            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

            clientPool.shutdownNow();   // Forza l'interruzione immediata dei thread client
            queryExecutor.shutdownNow(); // Forza l'interruzione immediata dei thread di esecuzione query
            scheduler.shutdownNow();     // Forza l'interruzione immediata dei thread dello scheduler

            // Attende qualche secondo per garantire che i thread si interrompano
            if (!clientPool.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Pool di client non terminato.");
            }
            if (!queryExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Esecutore di query non terminato.");
            }
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Scheduler non terminato.");
            }

            System.out.println("Server arrestato correttamente.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.exit(0); // Termina il programma forzatamente
        }
    }
}
