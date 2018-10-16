package cn.jun.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkCuratorWathcer {

    /**连接地址*/
    private static final String CONNECT_ADDR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    /**超时时间*/
    private static final int CONNECT_TIME = 200000;

    public static void main(String[] args) throws Exception{

        RetryPolicy policy = new ExponentialBackoffRetry(1000,10);

        CuratorFramework curator = CuratorFrameworkFactory
                .builder()
                .connectString(CONNECT_ADDR)
                .connectionTimeoutMs(CONNECT_TIME)
                .retryPolicy(policy)
                .build();

        curator.start();

        /**
         * NodeCache监听节点的新增与修改操作
         * 最后一个参数表示是否进行压缩
         */
        NodeCache nodeCache = new NodeCache(curator,"/super",false);
        nodeCache.start(true);
        //添加监听事件
        nodeCache.getListenable().addListener(() ->{
            System.out.println("Data:"+new String(nodeCache.getCurrentData().getData()));
            System.out.println("Path:"+nodeCache.getCurrentData().getPath());
            System.out.println("Stat:"+nodeCache.getCurrentData().getStat());
        });

        curator.create().forPath("/super","this is super".getBytes());
        curator.setData().forPath("/super","this is new super".getBytes());
        curator.delete().guaranteed().forPath("/super");
        System.out.println("------------------------------nodeCache------------------------------");
        /**
         * PathChildrenCache：监听子节点的新增、修改、删除操作。
         *第三个参数表示是否接收节点数据内容
         */
        PathChildrenCache childrenCache = new PathChildrenCache(curator,"/super",true);
        /**
         * 如果不填写这个参数，则无法监听到子节点的数据更新
         如果参数为PathChildrenCache.StartMode.BUILD_INITIAL_CACHE，则会预先创建之前指定的/super节点
         如果参数为PathChildrenCache.StartMode.POST_INITIALIZED_EVENT，效果与BUILD_INITIAL_CACHE相同，只是不会预先创建/super节点
         如果参数为PathChildrenCache.StartMode.NORMAL时，与不填写参数是同样的效果，不会监听子节点的数据更新操作
         */
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        childrenCache.getListenable().addListener((framework,event) -> {
            switch (event.getType()){
                case CHILD_ADDED:
                    System.out.println("-----------新增节点---------");
                    System.out.println("节点路径："+event.getData().getPath());
                    System.out.println("节点数据："+new String(event.getData().getData()));
                    System.out.println("节点状态："+event.getData().getStat());
                    System.out.println("节点类型："+event.getType());
                    System.out.println("----------------------------");
                    break;
                case CHILD_UPDATED:
                    System.out.println("-----------修改节点---------");
                    System.out.println("节点路径："+event.getData().getPath());
                    System.out.println("节点数据："+new String(event.getData().getData()));
                    System.out.println("节点状态："+event.getData().getStat());
                    System.out.println("节点类型："+event.getType());
                    System.out.println("----------------------------");
                    break;
                case CHILD_REMOVED:
                    System.out.println("-----------删除节点---------");
                    System.out.println("节点路径："+event.getData().getPath());
                    System.out.println("节点数据："+new String(event.getData().getData()));
                    System.out.println("节点状态："+event.getData().getStat());
                    System.out.println("节点类型："+event.getType());
                    System.out.println("----------------------------");
                    break;
                    default:
            }
        });

        curator.create().forPath("/super","this is super".getBytes());
        curator.create().creatingParentsIfNeeded().forPath("/super/children","this is children".getBytes());
        curator.setData().forPath("/super","this is new super".getBytes());
        curator.setData().forPath("/super/children","this is new children".getBytes());
        curator.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
        System.out.println("------------------------------pathChildrenCache------------------------------");
        Thread.sleep(500);
        /**
         * TreeCache：既可以监听节点的状态，又可以监听子节点的状态。类似于上面两种Cache的组合
         */
        TreeCache treeCache = new TreeCache(curator,"/super");
        treeCache.start();
        treeCache.getListenable().addListener((framework,event) ->{

            switch (event.getType()){
                case NODE_ADDED:
                    System.out.println("-----------新增节点---------");
                    System.out.println("节点路径："+event.getData().getPath());
                    System.out.println("节点数据："+new String(event.getData().getData()));
                    System.out.println("节点状态："+event.getData().getStat());
                    System.out.println("节点类型："+event.getType());
                    System.out.println("----------------------------");
                    break;
                case NODE_UPDATED:
                    System.out.println("-----------修改节点---------");
                    System.out.println("节点路径："+event.getData().getPath());
                    System.out.println("节点数据："+new String(event.getData().getData()));
                    System.out.println("节点状态："+event.getData().getStat());
                    System.out.println("节点类型："+event.getType());
                    System.out.println("----------------------------");
                    break;
                case NODE_REMOVED:
                    System.out.println("-----------删除节点---------");
                    System.out.println("节点路径："+event.getData().getPath());
                    System.out.println("节点数据："+new String(event.getData().getData()));
                    System.out.println("节点状态："+event.getData().getStat());
                    System.out.println("节点类型："+event.getType());
                    System.out.println("----------------------------");
                    break;
                default:
            }
        });
        curator.create().forPath("/super","this is super".getBytes());
        curator.create().creatingParentsIfNeeded().forPath("/super/children","this is children".getBytes());
        curator.setData().forPath("/super","this is new super".getBytes());
        curator.setData().forPath("/super/children","this is new children".getBytes());
        curator.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
        System.out.println("------------------------------treeCache------------------------------");
        Thread.sleep(500);

    }

}
