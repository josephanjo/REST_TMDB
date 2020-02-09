package com.TMDB.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.TMDB.utilities.ReadConfig;
import com.TMDB.utilities.XLUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
/*
 *  This test will search for a particular movie name when the Movie ID is given as an input
 *  It will verify that the Movie is listed successfully by validating the status code as 200 
 *  and the name of the movie
 */
public class TC01_SearchMovies extends baseClass{


	
	@Test()
	public void searchMovies() throws IOException
	{
		
		Common_Methods common=new Common_Methods();
		String [][] inputData=common.getData("TC_TestData.xlsx","TC01");//pass the name of test data file and Sheet name
		
		//Read test data from excel 
		String baseUri = inputData[0][0];//extract base URI
		String movieId= inputData[0][1];
		String statusCode= inputData[0][2];
		String movieName= inputData[0][3];

		//Construct EndPoint
		String endPoint= String.join("",movieId,"?api_key=",apiKey);
		Response response = common.invokeRestApi(Method.GET,baseUri,endPoint,null,null);
		
		//Response Status Code Validation
		common.responseStatusCodeValidation(statusCode,response.getStatusCode());
		
		//Movie Name Validation
		JsonPath jsonPath=response.jsonPath();
		System.out.println("Movie Name is "+jsonPath.get("belongs_to_collection.name"));
		Assert.assertEquals(jsonPath.get("belongs_to_collection.name"), movieName);
		
	}
	

}
