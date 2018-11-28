import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerThread implements  Runnable {

    private Socket client;
    private InputStream ins;
    private OutputStream out;

    private PrintWriter pw;
    private BufferedReader br;


    ServerThread(Socket client){
        this.client = client;
        init();
    }

    private void init() {

        try {
             ins = client.getInputStream();
             out = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            go();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void go() throws IOException {


        //读取请求内容
        int len = 0;
        byte[] b = new byte[1024];
        BufferedReader bf = new BufferedReader(new InputStreamReader(ins));
        String line = bf .readLine();
        System.out.println(line);

        //给用户响应
        pw = new PrintWriter(out);
        InputStream i = new FileInputStream("/Users/wxt/Desktop/project/httpServer/webroot/index.html");
        BufferedReader fr = new BufferedReader(new InputStreamReader(i));
        pw.println("HTTP/1.1 200 OK");
        pw.println("Content-type:text/html;charset=utf-8");
        pw.println("Content-Length: "+i.available());
        pw.println("Server: hello");
        pw.println("Date"+new Date());

        pw.println();
        pw.flush();
        String c = null;

        while ((c = fr.readLine())!=null){
            pw.print(c);
        }
        pw.flush();

        fr.close();
        pw.close();
        bf.close();
    }
}
