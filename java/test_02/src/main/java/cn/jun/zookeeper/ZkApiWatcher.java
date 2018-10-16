package cn.jun.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;


import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZkApiWatcher implements Watcher {

    /**连接地址*/
    private static final String CONNECT_ADDR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    /**超时时间*/
    private static final int CONNECT_TIME = 200000;
    /**信号量*/
    private static final CountDownLatch connectSemaphore = new CountDownLatch(1);

    private ZooKeeper zk = null;

    /**
     * 创建连接
     * @param addr :连接地址
     * @param timeOut ：超时时间
     */
    public void createConnection(String addr,int timeOut){
        this.releaseConnection();
        try{
            zk = new ZooKeeper(
                    addr,
                    timeOut,
                    this
            );
            System.out.println("开始连接zk服务器....");
            connectSemaphore.await();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 释放连接
     */
    public void releaseConnection(){
        if(zk!=null){
            try{
                this.zk.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建节点
     * @param path
     * @param data
     * @return
     */
    public String createNode(String path,String data){
        try{
            zk.exists(path,true);
            String node = zk.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            System.out.println("创建节点:"+node+"---------------");
            return node;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除节点
     * @param path
     * @return
     */
    public boolean deleteNode(String path){
        try{
            zk.delete(path,-1);
            System.out.println("删除节点:"+path+"---------------");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获得节点
     * @param path
     * @param needWatch
     * @return
     */
    public String getNode(String path,boolean needWatch){
        try{
            String data = new String(zk.getData(path,needWatch,null));
            return data;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改节点
     * @param path
     * @param data
     * @return
     */
    public Stat setNode(String path,String data){
        try{
            Stat stat = zk.setData(path,data.getBytes(),-1);
            System.out.println("修改节点:"+path+"---------------");
            return stat;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得子节点
     * @param path
     * @return
     */
    public List<String> getChildren(String path,boolean needWatch){
        try{
            return zk.getChildren(path,needWatch);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断节点是否存在
     * @param path
     * @return
     */
    public Stat existsNode(String path,boolean needWatch){
        try{
            return zk.exists(path,needWatch);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("进取process----------event = "+event);
        try{
            Thread.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(event==null){
            return ;
        }
        //连接状态
        Event.KeeperState state = event.getState();
        //事件类型
        Event.EventType type = event.getType();
        //受影响的路径
        String path = event.getPath();

        System.out.println("收到watch通知");
        System.out.println("连接状态:"+state);
        System.out.println("事件类型:"+type);

        if(Event.KeeperState.SyncConnected==state){
            if(Event.EventType.None==type){
                System.out.println("成功连接上服务器....");
                connectSemaphore.countDown();
            }
            else if (Event.EventType.NodeDataChanged==type){
                System.out.println("节点数据变更++++++++++");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(Event.EventType.NodeCreated==type){
                System.out.println("创建节点++++++++++");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(Event.EventType.NodeDeleted==type){
                System.out.println("删除节点++++++++++");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(Event.EventType.NodeDataChanged==type){
                System.out.println("子节点数据变更++++++++++");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else if (Event.KeeperState.Disconnected==state){
            System.out.println("服务器断开连接+++++++++");
        }else if (Event.KeeperState.AuthFailed==state){
            System.out.println("权限检查失败+++++++++");
        }else if(Event.KeeperState.Expired==state){
            System.out.println("会话失效+++++++++++");
        }
        System.out.println("++++++++++++++++++++++");
    }

    public static void main(String[] args) throws Exception{
        ZkApiWatcher zk = new ZkApiWatcher();
        zk.createConnection(CONNECT_ADDR,CONNECT_TIME);
        Thread.sleep(1000);
        zk.createNode("/zkApi","newNode");
        Thread.sleep(300);
        zk.getNode("/zkApi",true);
        zk.setNode("/zkApi","secondNode");
        Thread.sleep(300);
        String data = zk.getNode("/zkApi",true);
        System.out.println(data);
        zk.deleteNode("/zkApi");
        Thread.sleep(300);
        zk.createNode("/lock","root lock");
    }

}
