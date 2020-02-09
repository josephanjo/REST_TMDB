package com.TMDB.testCases;

import java.io.IOException;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import io.restassured.response.Response;

/*
 *  This test will rate a movie name when the Movie ID and Guest Session ID is given as an input.
 *  The test case first creates a Guest Session ID and then uses it to call the API and rate the movie.
 *  It will verify that the Movie is rated successfully by validating the status code as 201 
 * 
 */

public class TC02_RateMovie extends baseClass{

	
	@Test
	public void rateMovie() throws IOException
	{
		
		Common_Methods common=new Common_Methods();

		//Create a Guest Session ID
		String guestSessionId = common.getGuestSessionId(apiKey, guestSessionIdUri);
		
		//Read test data from excel 
		String [][] inputData=common.getData("TC_TestData.xlsx","TC02");//pass the name of test data file and Sheet name
		String baseUri= inputData[0][0];//extract base URI
		String movieRating= inputData[0][1];
		String movieId= inputData[0][2];
		String statusCode= inputData[0][3];
		
		//Request Payload
		JSONObject reqParams = new JSONObject();
		reqParams.put("value", movieRating);
		String requestBody=reqParams.toJSONString();		
		
		//Construct EndPoint
		String endPoint= String.join("","/",movieId,"/rating?api_key=",apiKey,"&guest_session_id=",guestSessionId);
		Response response = common.invokeRestApi(Method.POST,baseUri,endPoint,contentType,requestBody);	
		
		//Response Status Code Validation
		common.responseStatusCodeValidation(statusCode,response.getStatusCode());
	
}	
	
	

}
