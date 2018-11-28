package NIO;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 实现服务端与客户端的简单通信
 */
public class NIOClient {

    private  static  int flag = 0;
    private static   int blockSize = 4096;

    //发送数据的缓冲区
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(blockSize);
    //接受数据的缓冲区
    private static ByteBuffer receiveBuffer = ByteBuffer.allocate(blockSize);
    private final static InetSocketAddress serverAddress = new InetSocketAddress("localhost",8080);


    public static  void main(String []args) throws IOException {
        SocketChannel  socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        //打开选择器
        Selector selector = Selector.open();
        socketChannel.register(selector,SelectionKey.OP_CONNECT);
        socketChannel.connect(serverAddress);

        Set<SelectionKey> selectionKeys ;

        Iterator<SelectionKey> iterator;
        SelectionKey selectionKey;
        SocketChannel client;
        String receivText;
        String sendText;
        int count = 0;

        while (true){
            //选择一组建，其相应的通道已经为I/O操作准备就绪
            selector.select();
           selectionKeys =  selector.selectedKeys();
           iterator = selectionKeys.iterator();
           while (iterator.hasNext()){
               selectionKey = iterator.next();
               if (selectionKey.isConnectable()){
                   System.out.println("client is connecting");
                   client = (SocketChannel) selectionKey.channel();
                   if (client.isConnectionPending()){
                       client.finishConnect();
                       System.out.println("客户端完成连接操作");
                       sendBuffer.clear();

                       sendBuffer.put("你好，Server....".getBytes());
                       sendBuffer.flip();
                       client.write(sendBuffer);

                   }
                   client.register(selector,SelectionKey.OP_READ);
               }else  if(selectionKey.isReadable()){
                   client =  (SocketChannel) selectionKey.channel();
                   receiveBuffer.clear();
                   count = client.read(receiveBuffer);
                   if (count>0){
                       receivText = new String(receiveBuffer.array(),0,count);
                       System.out.println("客户端接收到服务端数据："+receivText);
                       client.register(selector,SelectionKey.OP_WRITE);
                   }
               }else if (selectionKey.isWritable()){
                   sendBuffer.clear();
                   client = (SocketChannel) selectionKey.channel();
                   sendText = "Msg send to server->"+flag++;
                   sendBuffer.put(sendText.getBytes());
                   sendBuffer.flip();
                   client.write(sendBuffer);
                   System.out.println("客户端发送数据给服务端："+sendText);
                   client.register(selector,SelectionKey.OP_READ);
               }
           }

           selectionKeys.clear();
        }
    }
}
