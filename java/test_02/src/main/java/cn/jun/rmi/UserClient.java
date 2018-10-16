package cn.jun.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;

public class UserClient {

    public static void main(String[] args) throws Exception{

        Context namingContext = new InitialContext();

        UserServiceServer userService = (UserServiceServer) namingContext.lookup("rmi://127.0.0.1:7000/getUser");

        System.out.println(userService.getUser("2"));

        System.out.println(userService);

    }

}
