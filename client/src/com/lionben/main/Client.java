package com.lionben.main;

import com.lionben.config.BaseConfig;
import com.lionben.pojo.ActiveMQSetting;
import com.lionben.pojo.Global;
import com.lionben.pojo.Message;
import com.lionben.sender.MessageSender;
import com.lionben.utils.SettingsUtils;
import org.apache.activemq.ActiveMQConnection;

/**
 * Created by lionben.  (lionbenx@gmail.com)
 */
public class Client extends BaseConfig{

    public static void main(String[] args)throws Exception{
        initSettings();
        ActiveMQSetting setting = getSettings() ;
        MessageSender messageSender = new MessageSender(setting) ;
        //发送信息
        for(int i=0;i<10;i++){
            Message message = new Message(i,"message"+i,"send a message") ;
            messageSender.sendMessage(message);
        }
        //发送完成释放资源
        messageSender.close();
    }

    /**
     * 得到setting的对象
     *
     * @return
     */
    public static ActiveMQSetting getSettings() {
        final String userName=SettingsUtils.getSettings(Global.settings, "activemq.userName", ActiveMQConnection.DEFAULT_USER);
        final String password=SettingsUtils.getSettings(Global.settings, "activemq.password",  ActiveMQConnection.DEFAULT_PASSWORD);
        final String tcpAddress=SettingsUtils.getSettings(Global.settings, "activemq.brokerURL",  "failover://tcp://192.168.4.244:61616");
        final String queueName=SettingsUtils.getSettings(Global.settings, "activemq.queuename", "monitorQueue");
        ActiveMQSetting activeMQSetting = new ActiveMQSetting(userName,password,tcpAddress,queueName);
        return activeMQSetting;
    }
}
