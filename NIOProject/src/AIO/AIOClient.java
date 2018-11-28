package AIO;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AIOClient {

    private AsynchronousSocketChannel client = null;
    AIOClient(String host,int port) throws IOException, ExecutionException, InterruptedException {
        client = AsynchronousSocketChannel.open();
        Future<?> future =  client.connect(new InetSocketAddress(host,port));
        System.out.println( future.get());
    }

    public void write(byte b){
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer.put(b);
        byteBuffer.flip();
        client.write(byteBuffer);
    }

    public static  void main(String args[]) throws InterruptedException, ExecutionException, IOException {
        AIOClient client = new AIOClient("localhost",7080);
        client.write((byte) 29);
    }
}
