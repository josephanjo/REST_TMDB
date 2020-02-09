package com.TMDB.testCases;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


/*
 *  This test will attempt to delete a list by providing an invalid API key
 *  It will verify that the response status code 401 is generated with appropriate message.
 *  
 */

public class TC12_DeleteListInvalidAPIKey extends baseClass{

	@Test
	public void deleteListInvalidApiKey() throws IOException
	{
		
		Common_Methods common=new Common_Methods();
		
		//Read test data from excel 
		String [][] inputData=common.getData("TC_TestData.xlsx","TC12");//pass the name of test data file and Sheet name
		String baseUri= inputData[0][0];//extract base URI
		String listId= inputData[0][1];
		String statusCode= inputData[0][2];
		String statusMessage= inputData[0][3];
		
		//Construct EndPoint
		String endPoint= String.join("","/3/list/",listId,"?api_key=xx",apiKey,"&session_id=",sessionId);
		Response response = common.invokeRestApi(Method.DELETE,baseUri,endPoint,null,null);
		
		//Response Status Code Validation
		common.responseStatusCodeValidation(statusCode,response.getStatusCode());
	
		//Status Message Validation
		JsonPath jsonPath=response.jsonPath();
		String responseStatusMessage = jsonPath.get("status_message");
		System.out.println("Status Message is "+responseStatusMessage);
		Assert.assertEquals(responseStatusMessage,statusMessage);
		
		}
}
