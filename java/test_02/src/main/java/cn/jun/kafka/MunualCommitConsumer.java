package cn.jun.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MunualCommitConsumer {


    public static void main(String[] args) {
        final Properties props = new Properties();
        //设置kafka集群地址
        props.put("bootstrap.servers", "192.168.81.131:9092,192.168.81.132:9092,192.168.81.133:9092");
        //设置消费者组，组名字自定义，组名字相同的消费在一个组
        props.put("group.id", "test");
        //开启offset自动提交
        props.put("enable.auto.commit", "false");
        //自动提交时间间隔
        props.put("auto.commit.interval.ms", "1000");
        //序列化器
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //实例化一个消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //消费者订阅主题，可以订阅多个主题
        consumer.subscribe(Arrays.asList("baseTopic"));
        final int minBatchSize = 50;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
                //insertIntoDb(buffer);
                for (ConsumerRecord bf : buffer) {
                    System.out.printf("offset = %d, key = %s, value = %s%n", bf.offset(), bf.key(), bf.value());
                }
                consumer.commitSync();
                buffer.clear();
            }
        }

    }

}
