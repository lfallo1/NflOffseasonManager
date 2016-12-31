package com.lancefallon.usermgmt.common.email.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.lancefallon.usermgmt.common.email.model.EmailConfig;

@Service
public class EmailService<T extends EmailConfig> {
	
    @Autowired
    private VelocityEngine velocityEngine;
    
    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendMail(T emailConfig) {
        try {
        	
        	MimeMessage message = mailSender.createMimeMessage();
        	MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            //set recipient / sender / subject
            helper.setTo(emailConfig.getTo());            
            helper.setSubject(emailConfig.getSubject());

            //create the model for view
            Map<String, Object> model = new HashMap<>();
            model.put("emailConfig", emailConfig);

            //load appropriate template, and generate html using velocity view engine
            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, emailConfig.getTemplate(), "UTF-8", model);
            helper.setText(text);
            mailSender.send(message);
            System.out.println("Email has been sent");
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
