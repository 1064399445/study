package cn.jun.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {

    public static final String URL = "tcp://192.168.81.135:61616";

    //jms方式的生产者
    public static void jmsProducer() throws Exception{
        //创建连接工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                URL);
        //通过工厂创建一个连接
        Connection connection = factory.createConnection();
        //启动连接
        connection.start();
        //创建一个session会话
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        //创建一个destination消息队列
        Destination destination = session.createQueue("myActiveMq");
        //创建消息创建者
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //创建一条消息
        TextMessage textMessage = session.createTextMessage("你好呀,我是jms");
        producer.send(textMessage);
        //提交会话
        session.commit();
        session.close();
        connection.close();
    }


    public static void main(String[] args) throws Exception{
        jmsProducer();
    }

}
