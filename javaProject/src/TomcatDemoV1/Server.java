package TomcatDemoV1;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务类
 */
public class Server {

    //计数器
    private  static  int count = 0;

    public static  void main(String []args){

        //提升作用域
        ServerSocket ss = null;
        Socket socket =null;

        try {
            ss = new ServerSocket(8080);
            System.out.println("服务器已经初始化了，等待客户端连接中");
            while (true){
                socket = ss.accept();
                count++;
                System.out.println("第"+count+"次请求连接服务器");

                //============拿到客户端信息======================
                //获取客户端的输入流，有相应的请求信息
                InputStream is = socket.getInputStream();
                byte[] buffer = new byte[1024];
                //输入流每次读出来的信息放入Buffer；
                int len = is.read(buffer);

                if (len>0){
                    String msg = new String(buffer,0,len);
                    System.out.println("-----"+msg+"-----");
                }else {
                    System.out.println("bad Request");
                }


                //=============发送服务返回信息====================
                OutputStream os= socket.getOutputStream();
                String html = "hello World";
                os.write(html.getBytes());
                os.flush();
                os.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
