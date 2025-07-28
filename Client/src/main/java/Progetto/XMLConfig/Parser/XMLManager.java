package Progetto.XMLConfig.Parser;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class XMLManager {
    // XMLReader si occuperà di avvaire il parsing(il parsing è la lettura del file xml)
    private XMLReader xmlReader;
    // AbstractHandler è l'oggetto che serve per riconoscere gli eventi (es. per leggere i caratteri se ne occupa l'evento characters ecc.)
    private AbstractHandler handler;
    // Contiene il percorso del file xml
    private String filename;

    // Costruttore
    public XMLManager(String filename, AbstractHandler handler) {
        this.filename = filename;
        try {
            // SAXParser serve per analizzare e leggere i file xml
            // newInstance() crea un oggetto SAXParse
            // newSAXParser è utilizzato per analizzare XML in modalità event-driven, cioè quando legge il file XML, genera eventi (come l'apertura di un tag, la lettura del contenuto di un tag, la chiusura di un tag, ecc.) e richiede la presenza di un gestore (handler) per trattare questi eventi.
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            // Stiamo estraendo l'oggetto XMLReader dal saxParser, questo oggetto è il componente che effettivamente esegue il parsing di un file xml nel modello SAX
            xmlReader = saxParser.getXMLReader();
            // Definiamo l'handler nel costruttore
            this.handler = handler;
            // Viene quindi impostato il gestore degli eventi, che permetterà al lettore XML di utilizzare il gestore specificato per elaborare gli eventi durante il parsing.
            xmlReader.setContentHandler(handler);
        } catch (ParserConfigurationException|SAXException e) {
            System.out.println(e.getMessage());
        }
    }

    // Metodo che avvia il parsing
    public String[] parse() {
        try {
            // Avviene il parsing del file presente nel percorso
            xmlReader.parse(filename);
        } catch (IOException|SAXException e) {
            System.out.println(e.getMessage());
        }
        // Restituisce un array di stringhe
        return handler.getData();
    }
}
