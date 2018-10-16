package cn.jun.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class UserServer {

    public static void main(String[] args) throws Exception{

        UserServiceServer service = new UserServiceServerImpl();
        //注册通信端口
        Registry registry = LocateRegistry.createRegistry(7000);
        //注册通信路径
        Context namingContext = new InitialContext();
        namingContext.rebind("rmi://127.0.0.1:7000/getUser",service);

        System.out.println("server:服务端启动....");
        System.out.println(service);
        Thread.sleep(1000000);

    }

}
