package com.TMDB.testCases;

import java.io.IOException;
import org.testng.annotations.Test;

public class PreReq02_CreateSession {
	public class PreReq01_CreateSession extends baseClass{

		@Test
		public void createSession() throws IOException
		{
		Common_Methods common = new Common_Methods();

		//Create a Session ID
		common.getSessionId(apiKey, sessionIdUri, requestToken, contentType);

		}

	}
}
