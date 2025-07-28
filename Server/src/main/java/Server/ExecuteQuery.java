package Server;

import XMLConfig.Parser.XMLManager;
import XMLConfig.XMLData.DbConfig;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.*;

public class ExecuteQuery implements Runnable {
    /* Eseguo l'estrazione dei dati per la connessione al databse. */
    private static final String DBCONFIG_FILENAME = "src/main/resources/dbConfig.xml";
    static String[] dbData = new XMLManager(DBCONFIG_FILENAME, new DbConfig()).parse();
    private static final String url = String.format("jdbc:%s://%s:%s/%s", dbData[0], dbData[1], dbData[2], dbData[3]);
    private static final String username = dbData[4];
    private static final String password = dbData[5];
    private final ArrayList<String> parametri;
    private final ObjectOutputStream clientOutput;

    public ExecuteQuery(QueryWithClient queryWithClient) {
        this.parametri = queryWithClient.getParametri();
        this.clientOutput = queryWithClient.getClientOutput();
    }
    /* Quando si avvia questo Thread si connette al database controlla se è una select e invia l'oggetto al client.
    *  Se non esiste invia null se esiste ivnia la select sottoforma di oggetto(map), se è una create ecc invia true o false.*/
    public void run() {
        ResultSet resultSet = null;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            System.out.println(parametri);
            String query = QueryCmd.queryCheck(parametri);
            System.out.println(query);
            // Determina il tipo di query
            if (query.trim().toUpperCase().startsWith("SELECT")) {
                // Esegui la query SELECT
                resultSet = stmt.executeQuery(query);

                // Crea una lista per memorizzare le righe di risultato
                List<Map<String, Object>> results = new ArrayList<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Itera sui risultati della query
                while (resultSet.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(i);
                        row.put(columnName, value);
                    }
                    results.add(row);
                }

                // Verifica se ci sono risultati
                if (!results.isEmpty()) {
                    // Invia i risultati al client (puoi personalizzare questo oggetto da inviare)
                    clientOutput.writeObject(results);
                    clientOutput.flush();
                    System.out.println(results);
                } else {
                    // Nessun risultato, invia null al client
                    clientOutput.writeObject(null);
                    clientOutput.flush();
                }
            } else {
                // Esegui query di tipo INSERT, UPDATE, DELETE, CREATE, ecc.
                int affectedRows = stmt.executeUpdate(query);

                // Se le righe influenzate sono maggiori di 0, l'operazione è andata a buon fine
                boolean success = affectedRows > 0;
                if (!success) {
                    clientOutput.writeObject(null);
                    clientOutput.flush();
                } else {
                    clientOutput.writeObject("Comando eseguito.");
                    clientOutput.flush();
                }
            }
        } catch (SQLException e) {
            System.out.println("Errore: Database irraggiungibile.");
            e.printStackTrace();
            try {
                // Invia false al client in caso di eccezione
                clientOutput.writeObject(null);
                clientOutput.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (UnknownHostException e) {
            System.err.println("Errore: Host sconosciuto o indirizzo IP non valido.");
            e.printStackTrace();
        } catch (ConnectException e) {
            System.err.println("Errore: Connessione rifiutata o server non raggiungibile.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("Errore generico: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Chiudi risorse
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
/* Una volta che è finito questa istruzione questo Thread viene chiuso e muore.*/
