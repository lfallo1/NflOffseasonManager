package com.lancefallon.usermgmt.config.jms;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsTextMessageServiceImpl implements JmsTextMessageService {

	@Autowired
	private Queue textMessageQueue;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public void sendTextMessage(String msg) {
		this.jmsTemplate.convertAndSend(this.textMessageQueue, msg);
	}
}
