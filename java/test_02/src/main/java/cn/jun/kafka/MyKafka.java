package cn.jun.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyKafka {


    public static void main(String[] args){

        Properties props = ConfigProperties.getProperties();

        String[] groups = props.getProperty("group.id").split(",");

        ExecutorService executor = Executors.newFixedThreadPool(groups.length);

        int i = 0;
        for(String group : groups){
            props.setProperty("group.id",group);
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            TopicPartition p = new TopicPartition(props.getProperty("topic"),i);
            //指定消费topic的那个分区
            consumer.assign(Arrays.asList(p));
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        ConsumerRecords<String, String> records = consumer.poll(1000);
                        for (ConsumerRecord<String, String> record : records) {
                            System.out.printf(Thread.currentThread().getName()+"---:offset = %d, key = %s, value = %s%n",
                                    record.offset(), record.key(), record.value());
                        }
                    }
                }
            });
            i++;
        }
        executor.shutdown();

    }


}
class ConfigProperties{


    public static Properties getProperties(){
        Properties props = new Properties();
        try{
            // 使用InPutStream流读取properties文件
            InputStream in = ConfigProperties.class.getClassLoader().getResourceAsStream("cn/jun/kafka/kafka.properties");
            // 使用properties对象加载输入流
            props.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }
        return props;
    }


}