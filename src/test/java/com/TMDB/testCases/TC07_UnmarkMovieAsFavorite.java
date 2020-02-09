package com.TMDB.testCases;

import java.io.IOException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
/*
 *  This test will attempt to post a request to mark a movie as favorite by passing the movie ID 
 *  and value for favorite as TRUE.
 *  It will then verify that the movie is listed among the favorite movies
 *  After this a post request will be attempted to unmark this movie from the favorite list by 
 *  passing the same movie ID and value for favorite as FALSE.
 *  It will verify that the response is created by checking the status code 200 with appropriate message.
 *  It will then list all the favorite movies of the user using a GET request and verify if the movie id 
 *  updated by the test case is not present in the response.
 */
public class TC07_UnmarkMovieAsFavorite extends baseClass{

	@Test()
	public void UnmarkMovieFavorite() throws IOException
	{
////////////////////////Mark Movie as Favorite/////////////////////////////
		Common_Methods common=new Common_Methods();
		
		//Read test data from excel 
		String [][] inputData=common.getData("TC_TestData.xlsx","TC07");//pass the name of test data file and Sheet name
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
		String fav="TRUE";
		reqParams.put("favorite", Boolean.parseBoolean(fav));
		String requestBody=reqParams.toJSONString();		
		
		//Construct EndPoint
		String endPoint= String.join("","/3/account/0/favorite?api_key=",apiKey,"&session_id=",sessionId);
		Response response = common.invokeRestApi(Method.POST,baseUri,endPoint,contentType,requestBody);	
		
		
////////////////////////List all Favorite Movies and validate it/////////////////////////////
		
		//Construct EndPoint
		endPoint= String.join("","/3/account/0/favorite/movies?api_key=",apiKey,"&session_id=",sessionId);
		response = common.invokeRestApi(Method.GET,baseUri,endPoint,null,null);
		
		//Movie ID Validation
		JsonPath jsonPath=response.jsonPath();
		String movId=jsonPath.get("results.id").toString();
		System.out.println("Favorite Movie IDs - "+movId);
		Assert.assertTrue(movId.contains(movieId));	
	
////////////////////////Unmark Movie as Favorite/////////////////////////////
		
		//Request Payload
		reqParams = new JSONObject();
		reqParams.put("media_type", mediaType);
		reqParams.put("media_id", movieId);
		reqParams.put("favorite", Boolean.parseBoolean(favorite));
		requestBody=reqParams.toJSONString();		
		
		//Construct EndPoint
		endPoint= String.join("","/3/account/0/favorite?api_key=",apiKey,"&session_id=",sessionId);
		response = common.invokeRestApi(Method.POST,baseUri,endPoint,contentType,requestBody);	
		
		//Response Status Code Validation
		common.responseStatusCodeValidation(statusCode,response.getStatusCode());	
		
		//Status Message Validation
		jsonPath=response.jsonPath();
		String statMsg = jsonPath.get("status_message");
		System.out.println("Status Message is "+statMsg);
		Assert.assertEquals(statMsg,statusMessage);

////////////////////////List all Favorite Movies and validate Removal/////////////////////////////
		
		//Construct EndPoint
		endPoint= String.join("","/3/account/0/favorite/movies?api_key=",apiKey,"&session_id=",sessionId);
		response = common.invokeRestApi(Method.GET,baseUri,endPoint,null,null);
		
		//Movie ID Validation
		jsonPath=response.jsonPath();
		movId=jsonPath.get("results.id").toString();
		System.out.println("Favorite Movie IDs - "+movId);
		Assert.assertFalse(movId.contains(movieId));	
			
	}

}
