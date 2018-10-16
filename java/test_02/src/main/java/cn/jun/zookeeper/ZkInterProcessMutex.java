package cn.jun.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.CloseableUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZkInterProcessMutex {


    private static final int QTY = 5;
    private static final int REPETITIONS = QTY * 10;
    private static final String PATH = "/examples/locks";
    private static final String CONNECT_ADDR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    public static void main(String[] args) throws Exception {
        final FakeLimitedResource resource = new FakeLimitedResource();
        ExecutorService executor = Executors.newFixedThreadPool(QTY);
        try {
            for(int i=0; i<QTY; i++) {
                final int index = i;
                Callable<Void> task = () -> {
                    CuratorFramework curator = CuratorFrameworkFactory
                            .newClient(CONNECT_ADDR, new RetryNTimes(3, 1000));
                    curator.start();
                    try {
                        final ExampleClientThatLocks example = new ExampleClientThatLocks(curator, PATH, resource, "Client " + index);
                        for(int j=0; j<REPETITIONS; j++) {
                            example.doWork(10, TimeUnit.SECONDS);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        CloseableUtils.closeQuietly(curator);
                    }
                    return null;
                };
                executor.submit(task);
            }
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

/**
 * 模拟的公共资源，这个资源期望只能单线程的访问，否则会有并发问题。
 */
class FakeLimitedResource {

    private final AtomicBoolean inUse = new AtomicBoolean(false);

    public void use() throws Exception{

        if(!inUse.compareAndSet(false,true)){
            throw new IllegalStateException("一次只能一个客户端访问");
        }

        try {
            Thread.sleep((long) (3 * Math.random()));
        } finally {
            inUse.set(false);
        }

    }

}

/**
 * 负责请求锁、使用资源、释放锁这样一个完整的访问过程。
 */
class ExampleClientThatLocks {

    private final InterProcessMutex lock;

    private final FakeLimitedResource resource;

    private final String clientName;

    public ExampleClientThatLocks(CuratorFramework framework,String path, FakeLimitedResource resource, String clientName) {
        this.lock = new InterProcessMutex(framework,path);
        this.resource = resource;
        this.clientName = clientName;
    }

    public void doWork(long time, TimeUnit timeUnit) throws Exception{
        if(!lock.acquire(time,timeUnit)){
            throw new IllegalStateException(clientName +"不能获取锁");
        }
        System.out.println(clientName+"获得了锁----------");
        try{
            resource.use();
        }finally {
            System.out.println(clientName+"释放了锁");
            lock.release();
        }
    }
}
