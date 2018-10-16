package cn.jun.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserServiceServer extends Remote {

    public User getUser(String id) throws RemoteException;

}
