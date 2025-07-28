package Routine;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    public static ArrayList connessioneDatabase(String url, String username, String password,int a,String eventualeCodice){
        ResultSet rs = null;
        String query="";
        int rows=0;

        ArrayList<String> risultato = new ArrayList<>();


        // Creiamo la connessione
        try {
            // 1. Carica il driver JDBC (opzionale per le versioni più recenti di Java)
            //Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Connessione al database
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connessione al database riuscita!");

            // 3. Creiamo uno statement per eseguire query SQL
            Statement stmt = conn.createStatement();

            // 4. Esegui una query
            switch (a) {
                case 1:
                    query = RoutineQuery.ricercaNumeroMedici();
                    rs = stmt.executeQuery(query);

                    break;
                case 2:
                    query = RoutineQuery.ricercaCodiceFiscaleDipendenti();
                    rs = stmt.executeQuery(query);

                    break;
                case 3:
                    query= RoutineQuery.eliminazionePrimaDataQuery(eventualeCodice);
                    rows = stmt.executeUpdate(query);
                    break;
                case 4:
                    query = RoutineQuery.inseriscoProssimaDataQuery(eventualeCodice);
                    rows = stmt.executeUpdate(query);
                    break;
                case 5:
                    query= RoutineQuery.OttieniIdPrenotazioniObsoleteQuery();
                    rs = stmt.executeQuery(query);
                    break;
                case 6:
                    query= RoutineQuery.eliminazionePrenotazioniObsoleteQuery();
                    rows = stmt.executeUpdate(query);
                    break;
                default:
                    System.out.println("Errore, numero non corretto");
                    break;
            }

            // 5. Processa i risultati
            if (rs != null) {
                while (rs.next()) {
                    risultato.add(rs.getString(1));
                    //System.out.println(output + rs.getString(1));
                }
                rs.close();
                stmt.close();
                conn.close();
            }
            else{
                //System.out.println("ResultSet è nullo. Controlla la query o la connessione al database.");
                stmt.close();
                conn.close();
            }
            // 6. Chiudi la connessione


        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database!");
            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            System.out.println("Driver JDBC non trovato!");
//            e.printStackTrace();
//        }
        }
        return risultato;
    }


}

