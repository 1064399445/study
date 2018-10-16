package cn.jun.zookeeper;

import org.apache.zookeeper.*;

import java.util.*;
import java.util.concurrent.*;

public class ZkApiTimeLock {

    /**连接地址*/
    private static final String CONNECT_ADDR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    /**超时时间*/
    private static final int CONNECT_TIME = 200000;
    /**信号量*/
    private static final CountDownLatch connectSemaphore = new CountDownLatch(1);
    private static final CountDownLatch latch = new CountDownLatch(1);
    /**锁根目录*/
    private static final String root = "/lock";
    /**新建的lock*/
    private String lockid;
    /**zookeeper客户端*/
    ZooKeeper zk = null;

    public ZkApiTimeLock(){
        try{
            zk = new ZooKeeper(CONNECT_ADDR, CONNECT_TIME,
                    new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            Event.KeeperState state = event.getState();
                            Event.EventType type = event.getType();
                            if(Event.KeeperState.SyncConnected==state){
                                if(Event.EventType.None==type){
                                    System.out.println("连接成功....");
                                    connectSemaphore.countDown();
                                }
                            }
                        }
                    });
            System.out.println("开始连接服务器....");
            connectSemaphore.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean getLock(){
        try{
            //创建锁节点
            lockid = zk.create(
                    root+"/",
                    "lock哒哒哒".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL
            );
            System.out.println(Thread.currentThread().getName()+"创建锁节点:"+lockid+"------------");
            //获得所有的锁节点并排序
            List<String> list = zk.getChildren(root,true);
            TreeSet<String> set = new TreeSet<String>();
            for(String node : list){
                set.add(root+"/"+node);
            }
            SortedSet<String> lessThanMe = set.headSet(lockid);
            //判断当前新建节点是否是最小节点
            if(lockid.equals(set.first())){
                System.out.println(Thread.currentThread().getName()+"获得锁:"+lockid+"!!!!!!!!!!");
                return true;
            }else if(!lessThanMe.isEmpty()){
                String preLockId = lessThanMe.last();
                zk.exists(preLockId, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        if(Event.EventType.NodeDeleted==event.getType()){
                            latch.countDown();
                        }
                    }
                });
                latch.await();
                System.out.println(Thread.currentThread().getName()+"已经获得锁:"+lockid);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlock(){
        try{
            System.out.println(Thread.currentThread().getName()+"释放锁:"+lockid);
            zk.delete(lockid,-1);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                zk.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args){

        //设置核心线程池大小
        int corePoolSize = 10;
        //设置最大接受数
        int maximumPoolSiez = 12;
        //当前线程数大于corePoolSize、小于maximumPoolSize时，超出corePoolSize的线程数的生命周期
        long keepActiveTime = 200;
        //设置时间单位，秒
        TimeUnit timeUnit = TimeUnit.SECONDS;
        //设置工作队列
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSiez,keepActiveTime,timeUnit,workQueue);

        for(int i=0;i<10;i++){
            executor.submit(() ->{
                    ZkApiTimeLock lock = null;
                    try {
                        lock = new ZkApiTimeLock();
                        lock.getLock();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if(lock!=null){
                            lock.unlock();
                        }
                    }
            });
        }

    }


}
