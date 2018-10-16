package cn.jun.zookeeper;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZkApiCurd {

    /**连接地址*/
    private static final String CONNECT_ADDR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    /**连接超时时间*/
    private static final int CONNECT_TIME = 20000;

    /**信号量，用于阻塞直到连接成功*/
    private static final CountDownLatch connectSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception{

        ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, CONNECT_TIME, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //获取时间的状态
                Event.KeeperState state = watchedEvent.getState();
                Event.EventType type = watchedEvent.getType();
                //如果建立连接
                if(Event.KeeperState.SyncConnected==state){
                    if(Event.EventType.None==type){
                        connectSemaphore.countDown();
                        System.out.println("zk连接成功......");
                    }
                }
            }
        });
        connectSemaphore.await();
        //创建父节点
        String firstNode = zk.create(
                "/zkApi",
                "first persistenet node".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT_SEQUENTIAL
        );
        System.out.println(new String(firstNode+"创建成功!!!"));
        //创建子节点
        String children = zk.create(
                firstNode+"/children",
                "this is a children".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL
        );
        System.out.println(new String(children+"创建成功!!!"));
        String children1 = zk.create(
                firstNode+"/children1",
                "this is a children1".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL
        );
        System.out.println(new String(children1+"创建成功!!!"));
        //获取父节点
        byte[] bytes = zk.getData(firstNode ,false,null);
        System.out.println("获得新建父节点内容:"+new String(bytes));
        //获取子节点
        byte[] childrenBytes = zk.getData(children,false,null);
        System.out.println("获得新建子节点内容:"+new String(childrenBytes));
        List<String> list = zk.getChildren(firstNode,false);
        for(String str : list){
            System.out.println("---"+str+"---");
        }
        //修改节点的值，-1表示跳过版本检查，其他正数表示如果传入的版本号与当前版本号不一致，则修改不成功，删除是同样的道理。
        zk.setData(children,"this is a children ---second".getBytes(),-1);
        byte[] childrenBytes2 = zk.getData(children,false,null);
        System.out.println("获得修改子点内容:"+new String(childrenBytes2));
        //删除节点
        zk.delete(children1,-1);
        System.out.println(zk.exists(children1,false));
        zk.delete(children,-1);
        System.out.println(zk.exists(children,false));
        zk.delete(firstNode,-1);
        System.out.println(zk.exists(firstNode,false));
        zk.close();
        System.out.println(zk.getState().isConnected());

    }


}
