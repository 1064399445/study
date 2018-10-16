package cn.jun.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class DatagramPacketClient {

    public static void main(String[] args) throws  Exception{
        //1.创建一个端口7000
        DatagramSocket ds= new DatagramSocket(7001);
        //2.创建一个包接受数据
        byte[] b = new byte[10000];
        DatagramPacket dp = new DatagramPacket(b,0,1000);
        Scanner s = new Scanner(System.in);
        while(true){
            String str = s.nextLine();
            str = "7001:"+str;
            //4.发送数据:需要指定ip以及port
            DatagramPacket send =
                    new DatagramPacket(
                            str.getBytes(),
                            str.getBytes().length,InetAddress.getLocalHost(),
                            7000);
            ds.send(send);
            //3.接受数据存到packet中
            ds.receive(dp);
            System.out.println(new String(dp.getData()));
        }
    }


}
