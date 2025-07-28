package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class handleClient implements Runnable{
    private final Socket clientSocket;
    private final BlockingQueue<QueryWithClient> buffer;

    public handleClient(Socket clientSocket, BlockingQueue<QueryWithClient> buffer) {
        this.clientSocket = clientSocket;
        this.buffer = buffer;
    }
    /* Quando si avvia il Thread mi ricavo out e in del client socket*/
    /* Grazie al while(true) out e in non si chiudono e quindi è possibile inviare la risposta al client.*/
    public void run() {
        InetAddress address = clientSocket.getLocalAddress();
            try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())){
                /* Prendo in ingresso l'oggetto del client che converto in stringa per la query*/
            while (true) {
                ArrayList<String> parametri = (ArrayList<String>) in.readObject();
                /* Inserisco la query e l'out nel buffer. out per tenere traccia del client. */
                buffer.put(new QueryWithClient(parametri, out));
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            // Gestisce eccezioni dovute a errori di I/O, problemi di serializzazione o interruzioni
            System.out.println(address + " disconnesso.");
        }
    }
}
