package com.lionben.jdbc;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.Properties;
import com.lionben.utils.SettingsUtils;
/**
 * Created by chenlinben on 2015/4/4.
 */
public class BaseJdbc {


    public BaseJdbc(){

    }

    /**
     * 初始化数据库连接
     *
     * @param settings
     * @return
     */
    protected static DataSource initJdbc(Properties settings) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(SettingsUtils.getSettings(settings, "jdbc.driver", null));
        ds.setUrl(SettingsUtils.getSettings(settings, "jdbc.url", null));
        ds.setUsername(SettingsUtils.getSettings(settings, "jdbc.username", null));
        ds.setPassword(SettingsUtils.getSettings(settings, "jdbc.password", null));
        ds.setInitialSize(Integer.parseInt(SettingsUtils.getSettings(settings, "jdbc.init", "5")));
        ds.setMaxActive(Integer.parseInt(SettingsUtils.getSettings(settings, "jdbc.maxActive", "15")));
        ds.setMaxIdle(Integer.parseInt(SettingsUtils.getSettings(settings, "jdbc.maxIdle", "5")));
        ds.setMinIdle(Integer.parseInt(SettingsUtils.getSettings(settings, "jdbc.minIdle", "3")));
        return ds;
    }

}
