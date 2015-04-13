package com.lionben.main;


import com.lionben.config.BaseConfig;
import com.lionben.consumer.ConsumerMessage;
import com.lionben.pojo.ActiveMQSetting;
import com.lionben.pojo.Global;
import com.lionben.utils.SettingsUtils;
import org.apache.activemq.ActiveMQConnection;

/**
 * @author lionben (lionbenx@gmail.com)
 */
public class Server extends BaseConfig {

    /**
     * 执行程序
     * @param args
     */
    public static void main(String[] args) {
        try {
            initSettings();
            ActiveMQSetting setting = getSettings();
            System.out.println(setting) ;
            //ConsumerMessage consumer = new ConsumerMessage(setting);
            //执行队列信息接收
            //consumer.consumeMessage();
        } catch (Exception e) {
            System.out.println("start fail !");
            System.out.println("the exception is : " + e);
        }
    }

    /**
     * 得到setting的对象
     * @return
     */
    public static ActiveMQSetting getSettings() {
        boolean transaction = Boolean.parseBoolean(SettingsUtils.getSettings(Global.settings, "activemq.transaction", "true"));
        String userName = SettingsUtils.getSettings(Global.settings, "sender.username", ActiveMQConnection.DEFAULT_USER);
        String password = SettingsUtils.getSettings(Global.settings, "sender.password", ActiveMQConnection.DEFAULT_PASSWORD);
        String tcpAddress = SettingsUtils.getSettings(Global.settings, "activemq.brokerURL", "failover://tcp://192.168.4.244:61616");
        String queueName = SettingsUtils.getSettings(Global.settings, "activemq.queuename", "monitorQueue");
        String rootPath = SettingsUtils.getSettings(Global.settings, "root.path", null);
        String noticePath = SettingsUtils.getSettings(Global.settings, "root.noticePath", null);
        int threadCount = Integer.parseInt(SettingsUtils.getSettings(Global.settings, "activemq.threadcount", "5"));
        ActiveMQSetting activeMQSetting = new ActiveMQSetting(transaction, userName, password, tcpAddress, queueName, rootPath, noticePath, threadCount);
        return activeMQSetting;
    }

}