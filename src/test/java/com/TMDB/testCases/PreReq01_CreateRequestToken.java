package com.TMDB.testCases;

import java.io.IOException;

import org.testng.annotations.Test;

public class PreReq01_CreateRequestToken extends baseClass{

	@Test
	public void createRequestToken() throws IOException
	{
	Common_Methods common = new Common_Methods();
	common.createRequestToken(apiKey, sessionIdUri);
	
	
	
	}
}
