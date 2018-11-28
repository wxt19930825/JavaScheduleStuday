package AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

public class AIOServer {

    public AIOServer(int port) throws IOException {
        AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open()
                .bind(new InetSocketAddress(port));
        listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Void vi) {
                //接受下一个链接
                listener.accept(null,this);

                    handle(result);
            }

            @Override
            public void failed(Throwable exc, Void vi) {
                System.out.println("异步IO失败");
            }
        });
    }

    //真正逻辑
    public void  handle(AsynchronousSocketChannel ch) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        try {
            ch.read(byteBuffer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        byteBuffer.flip();
        System.out.println("服务端接收："+byteBuffer.get());
    }

    public static  void main(String args[]) throws Exception {
        AIOServer server = new AIOServer(7080);
        System.out.println("服务监听端口："+7080);
        Thread.sleep(10000);

    }
}
