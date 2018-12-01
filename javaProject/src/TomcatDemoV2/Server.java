package TomcatDemoV2;


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

    public static  void main(String []args) throws Exception {

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
                Request request = new Request(is);

                //=============发送服务返回信息====================
                OutputStream os= socket.getOutputStream();
                Response response = new Response(os);

                //=============业务逻辑===========================
                String url = request.getUrl();
                if (isStaticResource(url)){
                    response.writeFile(url.substring(1),url);
                }

                //判断这个是不是静态资源：html、css、images、js、

                socket.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ss.close();
        }
    }


    /**
     *  //判断这个是不是静态资源：html、css、images、js、
     */
    public static  boolean isStaticResource(String url){

        String[] suffixs = {"html","css","js","image","jpeg","jpg"};

        boolean flag = false;

        for (String suffix:suffixs){
            if (url.endsWith("."+suffix)){
                flag = true;
                break;
            }
        }

        return   flag;
    }
}
