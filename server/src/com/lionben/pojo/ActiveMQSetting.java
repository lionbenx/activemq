package com.lionben.pojo;

/**
 * Created by lionben.
 */
public class ActiveMQSetting {
    private boolean transaction = false;
    private String userName = null;
    private String password = null;
    private String tcpAddress = null;
    private String queueName = null;
    private String rootPath = null;
    private String noticePath = null;
    private int threadCount = 0;

    public ActiveMQSetting() {
        super();
    }

    public ActiveMQSetting(boolean transaction, String userName, String password, String tcpAddress, String queueName, String rootPath, String noticePath, int threadCount) {
        this.transaction = transaction;
        this.userName = userName;
        this.password = password;
        this.tcpAddress = tcpAddress;
        this.queueName = queueName;
        this.rootPath = rootPath;
        this.noticePath = noticePath;
        this.threadCount = threadCount;
    }

    public boolean isTransaction() {
        return transaction;
    }

    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
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

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getNoticePath() {
        return noticePath;
    }

    public void setNoticePath(String noticePath) {
        this.noticePath = noticePath;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public String toString() {
        return "ActiveMQSetting{" +
                "transaction=" + transaction +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", tcpAddress='" + tcpAddress + '\'' +
                ", queueName='" + queueName + '\'' +
                ", rootPath='" + rootPath + '\'' +
                ", noticePath='" + noticePath + '\'' +
                ", threadCount=" + threadCount +
                '}';
    }
}
