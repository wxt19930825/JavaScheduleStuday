package TomcatDemoV2;

import javax.imageio.stream.FileImageInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 响应的封装类：就是写信息给客户端的
 */
public class Response {

    private OutputStream os = null;

    public Response(OutputStream os) {
        this.os = os;
    }

    /**
     *
     * 响应动态输出请求
     * @param content
     */
    public void writeContent(String content) throws IOException {
        os.write(content.getBytes());
        os.flush();
        os.close();
    }

    /**
     * 响应静态输出请求
     * @param path
     */
    public void writerHtmlFile(String path) throws Exception {
        String htmlContent = FileUtils.getFileContent(path);
        writeContent(htmlContent);
    }

    /**
     * 写文件
     */
    public void writeFile(String path,String url) throws Exception {

        String[] suffixs = {"html","css","js","jpeg"};
        String responseHead = "";
        boolean flag = false;


        FileInputStream fis = new FileInputStream(path);
        if (url.endsWith("."+suffixs[0])){
            responseHead = "HTTP/1.1 200 ok\r\nContent-Type: text/html\r\nContent-Length: "+fis.available()+"\r\n\r\n";
        }else if (url.endsWith("."+suffixs[3])){
            responseHead = "HTTP/1.1 200 ok\r\nContent-Type: image/jpeg\r\nContent-Length: "+fis.available()+"\r\n\r\n";
        }

        byte[] buff = new byte[1024];
        int len = 0;
        os.write(responseHead.getBytes());
        while ((len=fis.read(buff))!=-1){
            os.write(buff,0,len);
        }
        os.flush();
        os.close();
    }
}
