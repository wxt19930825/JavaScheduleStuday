package SocketProgramme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {

    public static void main(String []args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        InputStream ins = socket.getInputStream();

        BufferedReader br= new BufferedReader(new InputStreamReader(ins));

        String msg;

        while (!((msg=br.readLine())==null)){
            System.out.println("客户端发过来的数据是："+msg);
        }

        ins.close();
        socket.close();
        serverSocket.close();
    }
}
