package cn.jun.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class TranscationProducer {


    public static void main(String[] args) throws Exception{
        Properties pros = new Properties();
        //设置kafka集群的地址
        pros.put("bootstrap.servers","192.168.81.131:9092,192.168.81.132:9092,192.168.81.133:9092");
        pros.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        pros.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //设置事务id
        pros.put("transactional.id", "my_transactional_id");
        //创建producer
        Producer<String,String> producer = new KafkaProducer<>(pros);
        //事务初始化
        producer.initTransactions();
        //开启事务
        producer.beginTransaction();
        //发送信息
        for(int i=0;i<100;i++){
            producer.send(new ProducerRecord<String,String>("baseTopic",i+"","info:"+i));
            System.out.println(i);
        }
        producer.commitTransaction();
        producer.close();

    }
}
