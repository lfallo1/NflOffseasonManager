package com.lancefallon.usermgmt.config.actuator.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.stereotype.Component;

@Component
public class CustomAppMvcEndpoint extends EndpointMvcAdapter {

	@Autowired
	public CustomAppMvcEndpoint(CustonAppEndpoint delegate) {
		super(delegate);
	}

}
