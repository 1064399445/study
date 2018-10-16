package cn.jun.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceServerImpl implements UserServiceServer,Serializable {

    @Override
    public User getUser(String id) throws RemoteException {

        List<User> list = new ArrayList<User>();
        list.add(new User("1","刘备","123"));
        list.add(new User("2","关羽","456"));
        list.add(new User("3","张飞","789"));
        for(User u : list){
            if(u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }
}
