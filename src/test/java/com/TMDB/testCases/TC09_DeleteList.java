package com.TMDB.testCases;

import java.io.IOException;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


/*
 *  This test will first create a list and list it using the List Id.
 *  It will then attempt to delete the list by providing a ListID that was just created.
 *  It will verify that the list is deleted and response status code 201 is generated with appropriate message.
 *  After this the test will attempt to get the details of the list that was just deleted by providing the list ID
 *  and verify that the status code 404 is returned indicating that the list is not available
 *   
 *   NOTE - This test case is currently failing since the server is returning 500 - Internal error when attempting 
 *   to delete the list 
 */

public class TC09_DeleteList extends baseClass{
	
	@Test
	public void deleteList() throws IOException
	{
////////////////////////Create a List/////////////////////////////		
		Common_Methods common=new Common_Methods();
		
		//Read test data from excel 
		String [][] inputData=common.getData("TC_TestData.xlsx","TC09");//pass the name of test data file and Sheet name
		String baseUri= inputData[0][0];//extract base URI
		String listName= inputData[0][1];
		String listDescription= inputData[0][2];
		String language= inputData[0][3];
		String statusCode= inputData[0][4];
		String statusMessage= inputData[0][5];
		
		//Request Payload
		JSONObject reqParams = new JSONObject();
		reqParams.put("name", listName);
		reqParams.put("description", listDescription);
		reqParams.put("language", language);
		String requestBody=reqParams.toJSONString();		
		
		//Construct EndPoint
		String endPoint= String.join("","/3/list?api_key=",apiKey,"&session_id=",sessionId);
		Response response = common.invokeRestApi(Method.POST,baseUri,endPoint,contentType,requestBody);	
		
		//Response Status Code Validation
		common.responseStatusCodeValidation(statusCode,response.getStatusCode());
		
		JsonPath jsonPath=response.jsonPath();
		int listId = jsonPath.get("list_id");
		System.out.println("List ID is "+listId);
		
////////////////////////List the created list/////////////////////////////
			
		//Construct EndPoint
		endPoint= String.join("","/3/list/",Integer.toString(listId),"?api_key=",apiKey);
		response = common.invokeRestApi(Method.GET,baseUri,endPoint,null,null);
		
		//Status Code Validation
		int responseStatusCode = response.getStatusCode();
		System.out.println("Status Code for List ID result is "+responseStatusCode);
		Assert.assertEquals(responseStatusCode, HttpStatus.SC_OK);
		
		//List Name Validation
		jsonPath=response.jsonPath();
		String respListId=jsonPath.get("id");
		Assert.assertEquals(Integer.parseInt(respListId),listId);

////////////////////////Delete the created list/////////////////////////////
		
		//Construct EndPoint
		endPoint= String.join("","/3/list/",Integer.toString(listId),"?api_key=",apiKey,"&session_id=",sessionId);
		response = common.invokeRestApi(Method.DELETE,baseUri,endPoint,null,null);
		
		//Response Status Code Validation
		common.responseStatusCodeValidation(statusCode,response.getStatusCode());
	
		//Status Message Validation
		jsonPath=response.jsonPath();
		String responseStatusMessage = jsonPath.get("status_message");
		System.out.println("Status Message is "+responseStatusMessage);
		Assert.assertEquals(responseStatusMessage,statusMessage);
		
////////////////////////Verify that the list is deleted/////////////////////////////
		
		//Construct EndPoint
		endPoint= String.join("","/3/list/",Integer.toString(listId),"?api_key=",apiKey);
		response = common.invokeRestApi(Method.GET,baseUri,endPoint,null,null);
		
		//Status Code Validation
		responseStatusCode = response.getStatusCode();
		System.out.println("Status Code is "+responseStatusCode);
		Assert.assertEquals(responseStatusCode, HttpStatus.SC_NOT_FOUND);
	}	

}
