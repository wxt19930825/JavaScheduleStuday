import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class httpserver {

    public static  void main(String []args){
        try {
            ServerSocket sc = new ServerSocket(8080);

            System.out.println("端口号：8080 服务已经启动");
            while (!Thread.interrupted()){
                Socket client =sc.accept();

                //获取输入输出流
                InputStream ins = client.getInputStream();
                OutputStream out = client.getOutputStream();

                //读取请求内容
                int len = 0;
                byte[] b = new byte[1024];
                BufferedReader bf = new BufferedReader(new InputStreamReader(ins));
                String line = bf .readLine();
                System.out.println(line);

                //给用户响应
                PrintWriter pw = new PrintWriter(out);
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
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
