package com.lancefallon.usermgmt.config.actuator.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.lancefallon.usermgmt.config.jms.JmsConfig;

@Component
public class HealthCheckListener {
	
    @JmsListener(destination = JmsConfig.textMsgHealthcheckQueue)
    public void onMessage(String msg){
        System.out.println("#### " + msg + " ###" );
    }
}
