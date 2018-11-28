package SocketProgramme;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketObjectDemo {

    public static void main(String []args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        InputStream ins = socket.getInputStream();

        ObjectInputStream ois = new ObjectInputStream(ins);

        User user = (User) ois.readObject();
        System.out.println("获取的用户信息是："+user.toString());
        ois.close();
        ins.close();
        socket.close();
        serverSocket.close();
    }
}
