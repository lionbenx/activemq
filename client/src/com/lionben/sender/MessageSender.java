package com.lionben.sender;

import com.lionben.pojo.ActiveMQSetting;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.io.Serializable;

/**
 * @author lionben
 */
public class MessageSender {

    private ConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    //默认优先级消息发送
    private MessageProducer producer = null;
    //优先级消息发送
    private MessageProducer priorProducer = null;

    public MessageSender(ActiveMQSetting amqSetting) {
        try {
            connectionFactory = new ActiveMQConnectionFactory(amqSetting.getUserName(),amqSetting.getPassword(),amqSetting.getTcpAddress());
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(amqSetting.getQueueName());
            producer = session.createProducer(destination);
            priorProducer = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据优先级发送信息到消息队列
     * @param message  发送的消息
     * @param priority 要发送的优先级
     */
    public void sendMessageToMQ(Serializable message, int priority) {
        try {
            //如果发送消息的优先级大于5 则采用优先级策略发送
            if (priority >= 5)
                sendMessage(message, priority);
            else
                sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送默认的序列化对象信息，并设置发送的优先级
     *
     * @param message  要发送的消息
     * @param priority 设置消息发送的优先级
     * @throws Exception
     */
    public void sendMessage(final Serializable message, final int priority) throws Exception {
        priorProducer.setPriority(priority);
        ObjectMessage objectMessage = session.createObjectMessage();
        priorProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        objectMessage.setObject(message);
        priorProducer.send(objectMessage);
    }

    /**
     * 发送默认的序列化对象信息
     *
     * @param message 要发送的消息对象
     * @throws Exception
     */
    public void sendMessage(final Serializable message) throws Exception {
        ObjectMessage objectMessage = session.createObjectMessage();
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        objectMessage.setObject(message);
        producer.send(objectMessage);
    }

    /**
     * 发送普通的文本信息
     *
     * @param messageInfo 要发送的消息内容
     * @throws Exception
     */
    public void sendMessage(String messageInfo) throws Exception {
        MessageProducer producer = session.createProducer(destination);
        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("发送消息 " + messageInfo);
        producer.send(textMessage);
        //session.commit();  
    }

    /**
     * 关闭会话连接
     *
     * @throws Exception
     */
    public void close() {
        try {
            if (session != null)
                session.close();
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
