Pending Tasks
-
1. Unit Testing  

2. Proper timeout handling when calling the supplier REST API end point. 


Areas of improvements 
-  
1. If Jobs and Workers in production are actually provided as a huge reply in JSON format, we will have to parse 
the json objects on the fly ( using jackson streaming API )

2. Jobs and Workers need to be cached and refreshed. Cache expiry period should be decided from a business perspective. 
Needless to say that the expiry period duration will be impacted if the supplier API utilizes HTTP cache-control headers. 

3. 
 