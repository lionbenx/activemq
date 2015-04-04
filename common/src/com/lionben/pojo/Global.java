package com.lionben.pojo;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.Properties;


public class Global {
	public static Properties settings;
	public static DataSource datasource;	
	public static Logger logger;
	public static Properties getSettings() {
		return settings;
	}
	public static void setSettings(Properties settings) {
		Global.settings = settings;
	}
	public static DataSource getDatasource() {
		return datasource;
	}
	public static void setDatasource(DataSource datasource) {
		Global.datasource = datasource;
	}
	
	
}
