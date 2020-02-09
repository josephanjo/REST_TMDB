Pre-Requisites

Step 1 - Replace the "API_Key" vale in the config.properties file located at REST_TMDB\Configuration\config.properties

Step 2 - Create a Request Token by running /PreReq01_CreateRequestToken.java located at 
REST_TMDB/src/test/java/com/TMDB/testCases

Step 3 - Approve the token generated in Step 1 by visiting the URL - https://www.themoviedb.org/authenticate/{REQUEST_TOKEN}
Example - https://www.themoviedb.org/authenticate/e4d01e33b27ae984d505a97fbb7dd8d54210b6ce/allow

Step 4 - Replace the "requestToken" value in the config.properties file located at REST_TMDB\Configuration\config.properties

Step 5 - Create a Session ID by running /PreReq02_CreateSession.java located at 
REST_TMDB/src/test/java/com/TMDB/testCases

Step 6 - Replace the "sessionId" value in the config.properties file located at REST_TMDB\Configuration\config.properties

============================================================================================================================

To run the test suite - 
Open Testng.xml and Run as TestNG Suite
NOTE - TC09_DeleteList is currently failing since the server is returning 500 - Internal error when attempting to delete the list 

To run a test case individually -
Open the desired test case from REST_TMDB/src/test/java/com/TMDB/testCases and run it as TestNG Test

To modify the test data -
Open the test data excel sheet located at REST_TMDB/src/test/java/com/TMDB/testData, navigate to the appropriate sheet and modify the data


