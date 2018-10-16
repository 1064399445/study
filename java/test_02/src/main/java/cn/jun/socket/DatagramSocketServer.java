package cn.jun.socket;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class DatagramSocketServer {

    public static void main(String[] args) throws  Exception{
        //1.创建一个端口7000
        DatagramSocket ds= new DatagramSocket(7000);
        //2.创建一个包接受数据
        byte[] b = new byte[10000];
        DatagramPacket dp = new DatagramPacket(b,0,1000);
        Scanner s = new Scanner(System.in);
        while(true){
            //3.接受数据存到packet中
            ds.receive(dp);
            System.out.println(new String(dp.getData()));
            String str = s.nextLine();
            str = "7000:"+str;
            //4.发送数据
            DatagramPacket send =
                    new DatagramPacket(
                            str.getBytes(),
                            str.getBytes().length,
                            dp.getAddress(),
                            dp.getPort());
            ds.send(send);
        }
    }

}
