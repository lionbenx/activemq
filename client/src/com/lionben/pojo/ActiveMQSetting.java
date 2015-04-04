package com.lionben.pojo;

/**
 * Created by lionben.
 */
public class ActiveMQSetting {

    private String userName ;
    private String password ;
    private String tcpAddress ;
    private String queueName;

    public ActiveMQSetting() {
    }

    public ActiveMQSetting(String userName, String password, String tcpAddress, String queueName) {
        this.userName = userName;
        this.password = password;
        this.tcpAddress = tcpAddress;
        this.queueName = queueName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTcpAddress() {
        return tcpAddress;
    }

    public void setTcpAddress(String tcpAddress) {
        this.tcpAddress = tcpAddress;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
