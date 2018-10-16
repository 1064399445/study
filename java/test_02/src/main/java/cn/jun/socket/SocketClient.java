package cn.jun.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) throws Exception{
        //1.建立连接到端口7000的服务端
        Socket socket = new Socket("localhost",7000);
        System.out.println("连接成功......");
        //2.获得输入输出流
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());

        Scanner scanner = new Scanner(System.in);
        byte[] b = new byte[10000];
        //3.发送数据
        do{
            //发送数据
            String str = scanner.nextLine();
            bos.write(("客户端:"+str).getBytes());
            bos.flush();
            System.out.println("客户端:"+str);
            //读取数据
            bis.read(b);
            System.out.println(new String(b));
        }while(!"exit".equals(new String(b)));
        //4.关闭数据
        socket.close();

    }
}
