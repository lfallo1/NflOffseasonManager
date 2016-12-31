package com.lancefallon.usermgmt.config.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * handle index route when app starts. this will start the angular application
 * also handles 404 redirects
 * @author lancefallon
 *
 */
@Controller
public class IndexController {

	/**
	 * default route
	 * @return
	 */
	@RequestMapping("/")
	public String getIndex(){
		return "index.html";
	}
	
	/**
	 * redirect 404 to the index (this also resets the url to mysite.com/)
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/404.html")
	public void pageNotFound(HttpServletResponse response) throws IOException {
		response.sendRedirect("/");
	}
	
}
