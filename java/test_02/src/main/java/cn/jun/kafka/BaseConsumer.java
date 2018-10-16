package cn.jun.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class BaseConsumer {

    public static void main(String[] args) {
        final Properties props = new Properties();
        //设置kafka集群地址
        props.put("bootstrap.servers", "192.168.81.135:9092");
        //设置消费者组，组名字自定义，组名字相同的消费在一个组
        props.put("group.id", "test");
        //开启offset自动提交
        props.put("enable.auto.commit", "true");
        //自动提交时间间隔
        props.put("auto.commit.interval.ms", "1000");
        //序列化器
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //实例化一个消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //消费者订阅主题，可以订阅多个主题
        consumer.subscribe(Arrays.asList("papi"));
        //死循环不停的从broker中拿数据
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n",
                        record.offset(), record.key(), record.value());
            }
        }

    }

}
