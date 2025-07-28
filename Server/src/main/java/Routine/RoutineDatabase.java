package Routine;

import XMLConfig.Parser.XMLManager;
import XMLConfig.XMLData.DbConfig;

import java.util.ArrayList;

public class RoutineDatabase {
    public  ArrayList<String> result;
    /* Eseguo l'estrazione dei dati per la connessione al databse. */
    private static final String DBCONFIG_FILENAME = "src/main/resources/dbConfig.xml";
    static String[] dbData = new XMLManager(DBCONFIG_FILENAME, new DbConfig()).parse();
    private static final String url = String.format("jdbc:%s://%s:%s/%s", dbData[0], dbData[1], dbData[2], dbData[3]);
    private static final String username = dbData[4];
    private static final String password = dbData[5];



    public ArrayList ottieniCodici() {
        result = DatabaseConnection.connessioneDatabase(url, username, password, 2, "");
        return result; // ritorna tutti i codici fiscali in un arraylist di stringhe
    }

    public void eliminazionePrimaData(String cod) {
        result = DatabaseConnection.connessioneDatabase(url, username, password, 3, cod);
    }

    public void inseriscoProssimaData(String cod) {
        result = DatabaseConnection.connessioneDatabase(url, username, password, 4, cod);
    }
    
    public void routineAggiornamentoOrari(){
        ArrayList<String> listaCodici;
        RoutineDatabase routine = new RoutineDatabase();


        listaCodici = routine.ottieniCodici();
        System.out.println(listaCodici);

        int i;
        for (i = 0; i < listaCodici.size(); i++) {
            //System.out.println(listaCodici.get(i));
            routine.eliminazionePrimaData(listaCodici.get(i));
            routine.inseriscoProssimaData(listaCodici.get(i));
        }
    }

    public ArrayList ottieniIdPrenotazioniObsolete(){
        result = DatabaseConnection.connessioneDatabase("jdbc:mysql://localhost:3306/Azienda_Ospedaliera_5", "root", "$Raim_MySql_!!2\"9)0=", 5, "");
        return result;
    }

    public void eliminazionePrenotazioniObsolete(){
        result = DatabaseConnection.connessioneDatabase("jdbc:mysql://localhost:3306/Azienda_Ospedaliera_5", "root", "$Raim_MySql_!!2\"9)0=", 6, "");
    }

    public void routineAggiornamentoPrenotazioni(){
        RoutineDatabase routine = new RoutineDatabase();

        ArrayList<String> listaId;
        listaId=routine.ottieniIdPrenotazioniObsolete();
        if(listaId.isEmpty()){
            System.out.println("Nessuna prenotazione obsoleta");
        }
        else{
            routine.eliminazionePrenotazioniObsolete();
            System.out.println("Prenotazioni Obsolete eliminate");
        }
    }

}