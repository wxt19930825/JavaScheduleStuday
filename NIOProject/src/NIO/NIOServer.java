package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    private int flag = 1;
    private int blockSize = 4096;

    //发送数据的缓冲区
    private ByteBuffer sendBuffer = ByteBuffer.allocate(blockSize);
    //接受数据的缓冲区
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(blockSize);
    //
    private Selector selector ;

    public NIOServer(int port) throws IOException {
        //打开服务端的套接字通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //非阻塞通信（设置是否阻塞）
        serverSocketChannel.configureBlocking(false);

        //检索与此通道关联的服务器套接字
        ServerSocket serverSocket = serverSocketChannel.socket();
        //绑定IP和端口
        serverSocket.bind(new InetSocketAddress(port));

        //打开选择器
        selector = Selector.open();

        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        System.out.println("Server Start Listening  at  port :"+port);
    }


    //监听事件
    public  void listen() throws IOException {
        while (true){
            //选择一组建，并且相应的通道已经打开
            selector.select();
            Set<SelectionKey> selectionKeySet =  selector.selectedKeys();
            Iterator<SelectionKey> iterable = selectionKeySet.iterator();
            while (iterable.hasNext()){

                SelectionKey selectionKey =  iterable.next();
                iterable.remove();

                //业务逻辑
                handleKey(selectionKey);
            }
        }
    }

    public void handleKey(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel server = null;
        SocketChannel client = null;
        String receviText;
        String sendText;
        int count = 0;
        if (selectionKey.isAcceptable()){
            //返回之前创建此建的通道
            server = (ServerSocketChannel) selectionKey.channel();
            client = server.accept();
            client.configureBlocking(false);
            client.register(selector,SelectionKey.OP_READ);
        }else if (selectionKey.isReadable()) {
            //获取之前创建的客户端通道
            client = (SocketChannel)selectionKey.channel();
            //服务器端的接受缓冲区清空
            receiveBuffer.clear();
            //把客户端的数据放到服务端的接受缓冲区，并获取数据缓冲区大小赋予count
            count = client.read(receiveBuffer);
            if (count >0) {
                receviText = new String(receiveBuffer.array(),0,count);
                System.out.println("服务端接收到客户端的信息："+receviText);
                client.register(selector,SelectionKey.OP_WRITE);
            }
        }else if(selectionKey.isWritable()){
            //发送缓冲区清空
            sendBuffer.clear();
            //获取客户端通道
            client  = (SocketChannel)selectionKey.channel();

            sendText = "msg send to client:"+flag++;
            //发送数据缓冲区存放需要发送的数据
            sendBuffer.put(sendText.getBytes());

            //将缓冲区个标识位复位，因为向缓冲区里面放数据,如果需要把数据发送给服务器，就要复位
            sendBuffer.flip();

            client.write(sendBuffer);
            System.out.println(" Server 已经发送数据给客户端："+sendText);
            client.register(selector,SelectionKey.OP_READ);
        }

    }

    public static  void main(String []args) throws IOException {
        int port  = 8080;
        NIOServer nioServer  = new NIOServer(port);
        nioServer.listen();
    }
}
