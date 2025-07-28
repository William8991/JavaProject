package Server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class Consumatore implements Runnable{
    private final BlockingQueue<QueryWithClient> buffer;
    private final ExecutorService queryExecutor;

    public Consumatore(BlockingQueue<QueryWithClient> buffer, ExecutorService queryExecutor) {
        this.buffer = buffer;
        this.queryExecutor = queryExecutor;
    }
    /* Quando viene avviato questo Thread preleva dal buffer l'elemento e avvia un nuovo Thread concatenato a questo
    *  per eseguire le query. Lo avvia attraverso query executor che permette di eseguire 5 query alla volta.*/
    public void run() {
        while (true) {
            try {
                QueryWithClient queryWithClient = buffer.take();
                /* Creo un Thread(filgio) di tipo executeQuery per eseguire la query e mandare la risposta. */
                queryExecutor.submit(new ExecuteQuery(queryWithClient));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
