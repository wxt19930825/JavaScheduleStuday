package SocketProgramme;

import java.io.*;
import java.net.Socket;

public class ClientSocketObjectDemo {

    public static  void main(String []args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8080);

        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        User user = new User("wxt","123456");
        oos.writeObject(user);

        PrintWriter pw = new PrintWriter(oos);
        pw.flush();
        pw.close();
        oos.close();
        os.close();
        socket.close();

    }
}
