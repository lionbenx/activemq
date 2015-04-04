package com.lionben.config;

import com.lionben.pojo.Global;
import com.lionben.utils.SettingsUtils;

/**
 * Created by lionben.
 */
public class BaseConfig {
    private final static String FILENAME = "settings.properties";
    private final static String FILE_ENCODING = "utf-8";

    /**
     * 初始化设置信息
     * @throws Exception
     */
    public static void initSettings() throws Exception {
        //获取全局设置配置信息
        Global.settings = SettingsUtils.initSettings(FILENAME, FILE_ENCODING);
    }

}
