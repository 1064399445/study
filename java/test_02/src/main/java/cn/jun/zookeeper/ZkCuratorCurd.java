package cn.jun.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class ZkCuratorCurd {

    private static final String CONNECT_ADDR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private static final int TIME_OUT = 20000;

    public static void main(String[] args )throws Exception{
        //重试策略，初始事件1秒，重试10次
        RetryPolicy policy = new ExponentialBackoffRetry(1000,10);
        //通过工厂创建curator客户端
        CuratorFramework curator = CuratorFrameworkFactory
                .builder()
                .connectString(CONNECT_ADDR)
                .connectionTimeoutMs(TIME_OUT)
                .retryPolicy(policy)
                .build();

        curator.start();
        //创建节点，curator使用fluent风格
        curator.create()
                //创建不存在的父节点
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                //添加回调
                .inBackground((framework,event)->{
                    System.out.println("Code:"+event.getResultCode());
                    System.out.println("Type:"+event.getType());
                    System.out.println("Path:"+event.getPath());
                })
                .forPath("/curator/parent","this is parent".getBytes());
        //为了能够看到回调信息
        Thread.sleep(5000);
        //获得节点数据
        String parentData = new String(curator.getData().forPath("/curator/parent"));
        System.out.println("parent:"+parentData);
        //判断节点是否存在
        Stat stat = curator.checkExists().forPath("/curator/parent");
        System.out.println(stat);
        //更新节点
        curator.setData().forPath("/curator/parent","this is new parent!!!".getBytes());
        System.out.println(new String(curator.getData().forPath("/curator/parent")));
        //删除节点
        curator.delete()
                //安全的删除节点
                .guaranteed()
                //删除孩子节点
                .deletingChildrenIfNeeded()
                .forPath("/curator");
        System.out.println(curator.checkExists().forPath("/curator/parent"));
        System.out.println(curator.checkExists().forPath("/curator"));
    }

}
