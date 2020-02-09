package com.TMDB.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	Properties pro;
	
	public ReadConfig()
	{
		File src = new File("./Configuration/config.properties");

		try {
			FileInputStream fis = new FileInputStream(src);
			pro = new Properties();
			pro.load(fis);
		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
		}
	}
	
	public String getAPI_Key()
	{
		String url=pro.getProperty("API_Key");
		return url;
	}
	public String getRequestToken()
	{
		String url=pro.getProperty("requestToken");
		return url;
	}
	
	public String getSessionId()
	{
		String url=pro.getProperty("sessionId");
		return url;
	}
	
	public String getContentType()
	{
		String url=pro.getProperty("contentType");
		return url;
	}
	
	public String getGuestSessionIdUri()
	{
		String url=pro.getProperty("guestSessionIdUri");
		return url;
	}
	
	public String getSessionIdUri()
	{
		String url=pro.getProperty("sessionIdUri");
		return url;
	}
}
