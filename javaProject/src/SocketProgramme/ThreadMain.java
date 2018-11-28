package SocketProgramme;

import java.io.IOException;

public class ThreadMain {

    public static  void main(String []args) throws IOException {

        ServerSocketThread thread = new ServerSocketThread();

        thread.start();
    }
}
