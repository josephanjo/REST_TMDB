package com.TMDB.testCases;

import java.io.IOException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/*
 *  This test will attempt to post a request to mark a movie as favorite by passing an Invalid MovieID
 *  It will verify that request could not be performed since the resource is not found  by validating 
 *  the status code as 404
 */



public class TC06_MarkMovieAsFav_InvalidMovieID extends baseClass{
	
	@Test
	public void markMovieFavoriteInvalidMovieId() throws IOException
	{
		
		Common_Methods common=new Common_Methods();
		
		//Read test data from excel 
		String [][] inputData=common.getData("TC_TestData.xlsx","TC06");//pass the name of test data file and Sheet name
		String baseUri= inputData[0][0];//extract base URI
		String movieId= inputData[0][1];
		String mediaType= inputData[0][2];
		String favorite= inputData[0][3];
		String statusCode= inputData[0][4];
		String statusMessage= inputData[0][5];		
		
		//Request Payload
		JSONObject reqParams = new JSONObject();
		reqParams.put("media_type", mediaType);
		reqParams.put("media_id", movieId);
		reqParams.put("favorite", Boolean.parseBoolean(favorite));
		String requestBody=reqParams.toJSONString();		
		
		//Construct EndPoint
		String endPoint= String.join("","/3/account/0/favorite?api_key=",apiKey,"&session_id=",sessionId);
		Response response = common.invokeRestApi(Method.POST,baseUri,endPoint,contentType,requestBody);	
		
		//Response Status Code Validation
		common.responseStatusCodeValidation(statusCode,response.getStatusCode());
			
		//Status Message Validation
		JsonPath jsonPath=response.jsonPath();
		String responseStatusMessage = jsonPath.get("status_message");
		System.out.println("Response Status Message is "+responseStatusMessage);
		Assert.assertEquals(responseStatusMessage, statusMessage);
		

	}
	

}
