package com.TMDB.testCases;


import org.testng.annotations.BeforeClass;


import com.TMDB.utilities.ReadConfig;

public class baseClass {

	ReadConfig readconfig = new ReadConfig();
	public String apiKey = readconfig.getAPI_Key();
	public String contentType = readconfig.getContentType();
	public String guestSessionIdUri = readconfig.getGuestSessionIdUri();
	public String sessionIdUri = readconfig.getSessionIdUri();
	public String requestToken = readconfig.getRequestToken();
	public String sessionId = readconfig.getSessionId();

	
}
