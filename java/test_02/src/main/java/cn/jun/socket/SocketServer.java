package cn.jun.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServer {

    public static void main(String[] args) throws Exception{
        //1.创建一个端口号为7000的服务端
        ServerSocket server = new ServerSocket(7000);
        //2.等待客户端连接
        System.out.println("正在等待连接......");
        Socket socket = server.accept();
        System.out.println("有人连接我......");
        //3.获得输入输出流
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        Scanner scanner = new Scanner(System.in);
        byte[] b = new byte[10000];
        //4.发送数据
        do{
            //读取数据
            bis.read(b);
            System.out.println(new String(b));
            //发送数据
            String str = scanner.nextLine();
            bos.write(("服务端:"+str).getBytes());
            bos.flush();
            System.out.println("服务端:"+str);
        }while(!"exit".equals(new String(b)));
        //5.关闭数据
        socket.close();
        server.close();

    }

}
