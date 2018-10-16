package cn.jun.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

public class MunualPollByPartitionConsumer {

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
        //消费者订阅主题，并设置要拉取的分区
        TopicPartition partition0 = new TopicPartition("baseTopic",0);
        consumer.assign(Arrays.asList(partition0));
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
            for (TopicPartition partition : records.partitions()) {
                List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                for (ConsumerRecord<String, String> record : partitionRecords) {
                    System.out.println("partition:  " + partition.partition() + " , " + record.offset() + ": " + record.value());
                }
                long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
            }
        }

    }


}
