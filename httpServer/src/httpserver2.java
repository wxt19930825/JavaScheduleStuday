import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class httpserver2 {

    public static  void main(String []args){
        try {
            ServerSocket sc = new ServerSocket(8080);

            System.out.println("端口号：8080 服务已经启动");
            while (!Thread.interrupted()){
                Socket client =sc.accept();

                new Thread(new ServerThread(client)).start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
