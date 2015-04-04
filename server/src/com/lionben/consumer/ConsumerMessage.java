package com.lionben.consumer;

import com.lionben.pojo.ActiveMQSetting;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerMessage implements MessageListener {

    //启用处理的线程数
    private ExecutorService pool = null;
    private ActiveMQConnectionFactory connectionFactory = null;
    //连接池
    private PooledConnectionFactory poolFactory = null;
    //单例共享连接
    //private SingleConnectionFactory singleConnection = null ;
    private Destination destination = null;
    private Connection connection = null;
    private Session session = null;
    private MessageConsumer consumer = null;
    //操作失败执行的次数
    private final int ECOUNT = 2;
    private ActiveMQSetting setting = null;

    //初始化配置
    public ConsumerMessage(ActiveMQSetting setting) {
        this.setting = setting;
    }

    /**
     * 初始化属性字段
     * @throws JMSException
     * @throws Exception
     */
    private void initialize() throws JMSException, Exception {
        pool = Executors.newFixedThreadPool(setting.getThreadCount());
        connectionFactory = new ActiveMQConnectionFactory(
                setting.getUserName(), setting.getPassword(), setting.getTcpAddress());
        poolFactory = new PooledConnectionFactory(connectionFactory);
        poolFactory.setMaxConnections(100);
        //singleConnection = new SingleConnectionFactory(poolFactory) ;
        connection = poolFactory.createConnection();
        session = connection.createSession(setting.isTransaction(), getTransactionMode(setting.isTransaction()));
        destination = session.createQueue(setting.getQueueName());
        consumer = session.createConsumer(destination);
    }

    /**
     * 消费消息
     * @throws JMSException
     * @throws Exception
     */
    public void consumeMessage() throws JMSException, Exception {
        initialize();
        connection.start();
        // Global.logger.info("Consumer:->Begin listening...");
        // 开始监听
        consumer.setMessageListener(this);
    }

    /**
     * 接收信息并处理
     */

    public void onMessage(final Message message) {
        if (message == null)
            return;
        if (setting.isTransaction())
            onMessageTransaction(message);
        else
            onMessageNonTransaction(message);
    }

    /**
     * 事务处理消息
     *
     * @param message
     */
    private void onMessageTransaction(final Message message) {
        try {
            onhandleMessage(message);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            rollback();
        }
    }

    /**
     * 非事务处理消息
     *
     * @param message
     */
    private void onMessageNonTransaction(final Message message) {
        pool.execute(new Runnable() {
            public void run() {
                try {
                    onhandleMessage(message);
                } catch (Exception e) {
                    redoOperator(message);
                }
            }
        });
    }


    /**
     * 对消息进行处理
     * @param message
     * @throws RuntimeException
     * @throws Exception
     */
    private void onhandleMessage(final Message message) throws RuntimeException, Exception {
        ObjectMessage objectMessage = (ObjectMessage) message;
        Object obj = objectMessage.getObject();
        obj = null;
    }

    /**
     * 设置信息的处理模式
     * @param transaction
     * @return
     */
    private int getTransactionMode(boolean transaction) {
        if (transaction)
            return Session.SESSION_TRANSACTED;
        else
            return Session.DUPS_OK_ACKNOWLEDGE;
    }

    /**
     * 事务回滚操作
     */
    private void rollback() {
        try {
            session.rollback();
        } catch (JMSException e) {
            System.out.println("rollback :" + e);
        }
    }

    /**
     * 失败重做消息
     * @param message 要重做的信息
     *
     */
    private void redoOperator(final Message message) {
        for (int i = 0; i < ECOUNT; i++) {
            try {
                onhandleMessage(message);
                break;
            } catch (RuntimeException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                //Global.logger.info(e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     *
     * @throws JMSException
     */
    public void close() throws JMSException {
        //Global.logger.info("Consumer:->Closing connection");
        if (consumer != null)
            consumer.close();
        if (session != null)
            session.close();
        if (connection != null)
            connection.close();
    }

}
