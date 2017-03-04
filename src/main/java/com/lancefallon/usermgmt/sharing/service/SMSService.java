package com.lancefallon.usermgmt.sharing.service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMSService {
	
	  // Find your Account Sid and Token at twilio.com/user/account
	  public static final String ACCOUNT_SID = "ACe308a1cc197ecd02b4d562026b254ca4";
	  public static final String AUTH_TOKEN = "65f2606fabafd0818992c0014292a69b";

	  public void sendTextMessage(String to, String body, String mediaUrl) {
	    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	    Message message = Message
	        .creator(new PhoneNumber("+14436376219"), new PhoneNumber(lookup(to)),
	            body)
	        .setMediaUrl(mediaUrl)
	        .create();

	    System.out.println(message.getSid());
	  }

	private String lookup(String to) {
		/**
		 * TODO curl -XGET "https://lookups.twilio.com/v1/PhoneNumbers/6156?Type=carrier&Type=caller-name" -u "{AccountSid}:{AuthToken}"
		 */
		return to;
	}

}
