package com.hjy.tim.client.service.impl;

import com.hjy.tim.client.util.SpringBeanFactory;
import com.hjy.tim.client.service.CustomMsgHandleListener;
import com.hjy.tim.client.service.MsgLogger;

/**
 * 自定义收到消息回调
 *
 * @since JDK 1.8
 */
public class MsgCallBackListener implements CustomMsgHandleListener {


    private MsgLogger msgLogger ;

    public MsgCallBackListener() {
        this.msgLogger = SpringBeanFactory.getBean(MsgLogger.class) ;
    }

    @Override
    public void handle(String msg) {
        msgLogger.log(msg) ;
    }
}
