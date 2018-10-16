package cn.jun.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class BaseProcuder {

    public static void main(String[] args) throws Exception{

        Properties pros = new Properties();
        //设置kafka集群的地址
        pros.put("bootstrap.servers","192.168.81.135:9092");
        //ack模式，all是最慢但是最安全的
        pros.put("acks","-1");
        //失败重试次数
        pros.put("retries",0);
        //每个分区发送消息总字节大小（字节），超过大小就会自动提交到服务端
        pros.put("batch.size",10);
        //消息在缓冲区保留的时间，超过时间就会发送到服务器
        pros.put("linger.ms",10000);
        //整个produce用到的总内存的大小，如果缓冲区满了会提交数据到服务端
        //buffer.memory要大于batch.size，不然会报内存不足
        pros.put("buffer.memory",10240);
        //序列化器
        pros.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        pros.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //创建producer
        Producer<String,String> producer = new KafkaProducer<>(pros);
        //发送信息
        for(int i=0;i<100;i++){
            producer.send(new ProducerRecord<String,String>("mykafka",i+"","info:"+i));
            System.out.println(i);
        }
        Thread.sleep(5000);
        System.out.println("success");
        producer.close();

    }



}
