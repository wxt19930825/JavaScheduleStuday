package SocketProgramme;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketThread extends  Thread {

    private ServerSocket serverSocket ;

    public ServerSocketThread() throws IOException {
        serverSocket = new ServerSocket(8080);
    }

    @Override
    public void run() {
        while (true){

            try {
                Socket socket = serverSocket.accept();;
                InputStream ins = socket.getInputStream();

                ObjectInputStream ois = new ObjectInputStream(ins);

                User user = (User) ois.readObject();
                System.out.println("获取的用户信息是："+user.toString());
                ois.close();
                ins.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
