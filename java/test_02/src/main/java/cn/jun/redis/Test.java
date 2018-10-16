package cn.jun.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args){
//        Jedis jedis = new Jedis("127.0.0.1",6379);
//        System.out.println(jedis.get("name"));
//
//        jedis.set("age","18");
//        System.out.println(jedis.get("age"));

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxWaitMillis(1000);
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.81.131",7001));
        nodes.add(new HostAndPort("192.168.81.132",7001));
        nodes.add(new HostAndPort("192.168.81.133",7001));
        nodes.add(new HostAndPort("192.168.81.131",7002));
        nodes.add(new HostAndPort("192.168.81.132",7002));
        nodes.add(new HostAndPort("192.168.81.133",7002));
        JedisCluster jedisCluster = new JedisCluster(nodes,poolConfig);
        jedisCluster.set("name","james");
        jedisCluster.set("age","18");
        System.out.println(jedisCluster.get("name"));
        System.out.println(jedisCluster.get("age"));


    }

}
