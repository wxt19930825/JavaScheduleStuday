package TomcatDemoV2;

import java.io.IOException;
import java.io.InputStream;

/**
 * 解析请求类：解析资源地址
 */
public class Request {

    private String url;
    //定义一个参数字符
    private String pString;

    public Request(InputStream ins) throws IOException {
        byte[] buffer = new byte[1024];
        //输入流每次读出来的信息放入Buffer；
        int len = ins.read(buffer);

        if (len>0){
            String msg = new String(buffer,0,len);
            int start = msg.indexOf("GET")+4;
            if (msg.indexOf("POST")!=-1){
                start = msg.indexOf("POST")+5;
            }

            int end = msg.indexOf("HTTP/1.1")-1;

            //当get请求带有参数的时候，则url会有变化

            //拿到请求资源路径
            url =msg.substring(start,end);
            if (msg.startsWith("POST")){
                int paraSart = msg.indexOf("\n");
                pString = msg.substring(paraSart+1);
            }
            System.out.println("资源请求路径："+url);
        }else {
            System.out.println("bad Request");
        }
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
