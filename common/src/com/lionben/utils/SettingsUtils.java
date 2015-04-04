package com.lionben.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author lionben
 */
public class SettingsUtils {

    /**
     * 得到文件的配置信息
     *
     * @param fileName 配置文件的名称
     * @param encoding 配置文件的编码
     * @return
     * @throws Exception
     */
    public static Properties initSettings(String fileName, String encoding) throws Exception {
        encoding = encoding == null ? "utf-8" : encoding;
        InputStream src = SettingsUtils.class.getClassLoader().getResourceAsStream(fileName);
        InputStreamReader reader = new InputStreamReader(src, encoding);
        Properties settings = new Properties();
        settings.load(reader);
        reader.close();
        src.close();
        return settings;
    }

    /**
     * 得到属性的值
     *
     * @param settings     属性列表
     * @param name         要获取的属性名称
     * @param defaultValue 获取失败的默认值
     * @return
     */
    public static String getSettings(Properties settings, String name, String defaultValue) {
        if (settings == null)
            return defaultValue;
        String result = settings.getProperty(name);
        if (result == null)
            return defaultValue;
        else
            return result;
    }

    /**
     * 得到属性的值
     *
     * @param settings 属性列表
     * @param name     要获取的属性名称
     * @return
     */
    public static String getSettings(Properties settings, String name) {
        String result = settings.getProperty(name);
        if (result == null)
            throw new RuntimeException(name + " not found!");
        return result;
    }

}
