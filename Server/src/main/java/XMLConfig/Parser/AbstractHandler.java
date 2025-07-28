package XMLConfig.Parser;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 Definisce un generico Handler per il parsing SAX
 da utilizzare quando l'XML è strutturato in modo che
 tutti i figli della root tag sono anche foglie
 */
public abstract class AbstractHandler extends DefaultHandler {

    /*
    Il parsing si basa sul fatto che il numero dei parametri
    da leggere è costante, di conseguenza è possibile saperlo a priori.
    È possibile leggere quindi tutti i parametri tramite un contatore
    e sapendo il nome del tag radice in modo da scartarlo
    */
    // Tiene traccia dell'indice corrente degli elementi letti, inizia da -1 poichè l'incremento avviene nel metodo startelement
    private int counter = -1;
    // Array di stringhe che memorizza i dati estratti dal file XML. La dimensione di questo array è determinata nel costruttore.
    private String[] data;
    // Questo flag indica se il parser sta attualmente leggendo il contenuto di un elemento. Viene utilizzato per controllare l'assegnazione dei valori all'array data.
    private boolean read = false;
    // Memorizza il nome del primo tag (radice) del file XML che deve essere ignorato durante il parsing.
    private String firstTagName;

    // chiamato dal costruttore dell'handler concreto
    // Viene passato il nome del primo tag e il numero di tag figli
    public AbstractHandler(String firstTagName, int childNumber) {
        this.firstTagName = firstTagName;
        // viene creata una stringa di dimensione pari al numero dei tag figli
        data = new String[childNumber];
    }

    // Rileva il tag di apertura
    @Override
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes atts) {
        if (!rawName.equals(firstTagName)) {
            counter++;
            read=true;
        }
    }

    // Rileva i caratteri ignorando i tag di apertura e chiusura
    @Override
    public void characters(char[] ch, int start, int length) {
        if (read) {
            data[counter] = new String(ch, start, length);
            read = false;
        }
    }

    // Ritona l'array di stringhe dove ogni stringa è la stringa tra i tag
    public String[] getData() {
        return data;
    }

}
