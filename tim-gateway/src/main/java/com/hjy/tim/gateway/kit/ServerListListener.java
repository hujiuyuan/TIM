package com.hjy.tim.gateway.kit;

import com.hjy.tim.gateway.config.AppConfiguration;
import com.hjy.tim.gateway.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since JDK 1.8
 */
public class ServerListListener implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ServerListListener.class);

    private ZKit zkUtil;

    private AppConfiguration appConfiguration;


    public ServerListListener() {
        zkUtil = SpringBeanFactory.getBean(ZKit.class);
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
    }

    @Override
    public void run() {
        //注册监听服务
        zkUtil.subscribeEvent(appConfiguration.getZkRoot());

    }
}
