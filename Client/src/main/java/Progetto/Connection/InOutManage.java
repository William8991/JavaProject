package Progetto.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InOutManage {
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public InOutManage(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }

    public synchronized void send(Object data) throws IOException {
        out.writeObject(data);
        out.flush();
    }

    public synchronized Object receive() throws IOException, ClassNotFoundException {
        return in.readObject();
    }
}
