package SocketProgramme;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketDemo {

    public static  void main(String []args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8080);

        OutputStream os = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(os);
        String request = "hello Server";
        writer.write(request);
        writer.flush();
        writer.close();
        socket.close();

    }
}
