package Server;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class QueryWithClient {
    private final ArrayList<String> parametri; // Query inviata dal client
    private final ObjectOutputStream clientOutput; // Flusso per inviare risultati al client

    public QueryWithClient(ArrayList<String> parametri, ObjectOutputStream clientOutput) {
        this.parametri = parametri;
        this.clientOutput = clientOutput;
    }

    public ArrayList<String> getParametri() {
        return parametri;
    }

    // Metodo per ottenere l'output stream del client
    public ObjectOutputStream getClientOutput() {
        return clientOutput;
    }
}
