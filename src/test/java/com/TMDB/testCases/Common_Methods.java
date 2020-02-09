package com.TMDB.testCases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.testng.Assert;

import com.TMDB.utilities.XLUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Common_Methods {
	
	public  FileInputStream fi;
	public  FileOutputStream fo;
	public  XSSFWorkbook wb;
	public  XSSFSheet ws;
	public  XSSFRow row;
	public  XSSFCell cell;
	
	public Response invokeRestApi(Method httpMethod, String baseURI, String endPoint, String contType,String body) 
	{
		System.out.println("Rest API Send Request : "+baseURI+endPoint);
		RestAssured.baseURI=(baseURI);
		RequestSpecification httpRequest = RestAssured.given();
		if(contType !=null)
			httpRequest.header("Content-Type",contType);
		if(contType !=body)
			httpRequest.body(body);
	
		//Response
		 Response response =  httpRequest.request(httpMethod,endPoint);
		 String resp = response.getBody().asString();
		 System.out.println("Response Body for Invoke Rest API is "+resp);
		 return response;
	}

	
	public String [][] getData(String excelPath, String sheetName) throws IOException
	{
		String path=System.getProperty("user.dir")+"/src/test/java/com/TMDB/testData/"+excelPath;
		int rownum=XLUtils.getRowCount(path, sheetName);
		int colcount=XLUtils.getCellCount(path,sheetName,1);
		String exceldata[][]=new String[rownum][colcount];
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				exceldata[i-1][j]=XLUtils.getCellData(path,sheetName, i,j);//1 0
			}
		}
	return exceldata;
	}
	
	public String getCellData(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
	{
		fi=new FileInputStream(xlfile);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(xlsheet);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		String data;
		try 
		{
			DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
		}
		catch (Exception e) 
		{
			data="";
		}
		wb.close();
		fi.close();
		return data;
	}
	
	public String getGuestSessionId(String apiKey,String guestSessionIdUri)
	{
				
		//Construct EndPoint
		String endPoint= String.join("","/new?api_key=",apiKey);
		Response response = invokeRestApi(Method.GET,guestSessionIdUri,endPoint,null,null);
		
		//Status Code Validation
		int responseStatusCode = response.getStatusCode();
		System.out.println("Status Code for Guest Session ID Generation is "+responseStatusCode);
		Assert.assertEquals(responseStatusCode, HttpStatus.SC_OK);
		
		//Guest Session ID Generation Validation
		JsonPath jsonPath=response.jsonPath();
		String tokenExpiry = jsonPath.get("expires_at");
		System.out.println("Guest Session ID Expires At "+tokenExpiry);
		String responseGuestSessionId = jsonPath.get("guest_session_id");
		System.out.println("Guest Session ID is "+responseGuestSessionId);
		
		return responseGuestSessionId;
	}
	
	public void responseStatusCodeValidation(String expectedStatusCode,int actualStatusCode)
	{
		System.out.println("Response Status Code is "+actualStatusCode);
		Assert.assertEquals(actualStatusCode, Integer.parseInt(expectedStatusCode));
	}
	
	public String getSessionId(String apiKey,String sessionIdUri, String requestToken, String contentType)
	{
		//Request Payload
		JSONObject reqParams = new JSONObject();
		reqParams.put("request_token", requestToken);
		String requestBody=reqParams.toJSONString();
				
		//Construct EndPoint
		String endPoint= String.join("","/session/new?api_key=",apiKey);
		Response response = invokeRestApi(Method.POST,sessionIdUri,endPoint,contentType,requestBody);
		
		//Status Code Validation
		int responseStatusCode = response.getStatusCode();
		System.out.println("Status Code for Session ID Generation is "+responseStatusCode);
		Assert.assertEquals(responseStatusCode, HttpStatus.SC_OK);
		
		//Session ID Generation Validation
		JsonPath jsonPath=response.jsonPath();
		String responseSessionId = jsonPath.get("session_id");
		System.out.println("Session ID is "+responseSessionId);
		
		return responseSessionId;
	}
	
	
	public String createRequestToken(String apiKey,String sessionIdUri)
	{
		
		String endPoint= String.join("","token/new?api_key=",apiKey);
		Response response = invokeRestApi(Method.GET,sessionIdUri,endPoint,null,null);
		
		//Status Code Validation
		int responseStatusCode = response.getStatusCode();
		System.out.println("Status Code for Request Token Generation is "+responseStatusCode);
		Assert.assertEquals(responseStatusCode, HttpStatus.SC_OK);
		
		//Guest Session ID Generation Validation
		JsonPath jsonPath=response.jsonPath();
		String tokenExpiry = jsonPath.get("expires_at");
		System.out.println("Request Token Expires At "+tokenExpiry);
		String responseRequestToken = jsonPath.get("request_token");
		System.out.println("Request Token is "+responseRequestToken);
		
		return responseRequestToken;
	}
	
}
