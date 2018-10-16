package cn.jun.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Customer {

    //jms方式创建消费者
    public static void jmsCustomer() throws Exception{
        //创建连接工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.81.135:61616");
        //通过工厂创建一个连接
        Connection connection = factory.createConnection();
        //开启连接
        connection.start();
        //创建一个session会话
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        //创建一个destination消息队列
        Destination destination = session.createQueue("myActiveMq");
        //创建一个消息消费者
        MessageConsumer consumer = session.createConsumer(destination);
        while (true){
            // 接收数据的时间（等待） 100 ms
            Message message = consumer.receive(100*1000);
            TextMessage textMessage = (TextMessage) message;
            if(textMessage!=null){
                System.out.println("consumer接收："+textMessage.getText());
            }
        }

    }

    public static void main(String[] args) throws Exception{

        jmsCustomer();


    }



}
