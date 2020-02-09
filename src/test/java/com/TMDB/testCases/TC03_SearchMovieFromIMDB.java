package com.TMDB.testCases;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/*
 *  This test will search for a particular movie name from IMDB when the IMDB Movie ID is given as an input.
 *  It will verify that the Movie is listed successfully by validating the status code as 200 
 *  and the name of the movie.
 */
public class TC03_SearchMovieFromIMDB extends baseClass{

	@Test()
	public void searchMoviefromImdb() throws IOException
	{
		
		Common_Methods common=new Common_Methods();
		String [][] inputData=common.getData("TC_TestData.xlsx","TC03");//pass the name of test data file and Sheet name
		
		//Read test data from excel 
		String baseUri = inputData[0][0];//extract base URI
		String imdbMovieId= inputData[0][1];
		String language= inputData[0][2];
		String externalSource= inputData[0][3];
		String statusCode= inputData[0][4];
		String movieName= inputData[0][5];

		//Construct EndPoint
		String endPoint= String.join("","/",imdbMovieId,"?api_key=",apiKey,"&language=",language,"&external_source=",externalSource);
		Response response = common.invokeRestApi(Method.GET,baseUri,endPoint,null,null);
		
		//Response Status Code Validation
		common.responseStatusCodeValidation(statusCode,response.getStatusCode());
		
		//Movie Name Validation
		JsonPath jsonPath=response.jsonPath();
		String movie=jsonPath.get("movie_results.title").toString();
		System.out.println("Movie Name is "+movie);
		Assert.assertEquals(movie, movieName);
		
	}

}
