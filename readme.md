Areas of improvements 
-  
1. If Jobs and Workers in production are actually provided as a huge reply in JSON format, we will have to parse 
the json objects on the fly ( using jackson streaming API ).

2. Jobs and Workers need to be cached and refreshed. Cache expiry period should make sense from a business perspective. 
Needless to say that the expiry period duration will be impacted if the supplier API utilizes HTTP cache-control headers. 

3. If the date is large, we have to parallelize the matching algorithm.

4. reWrite ProviderDataCacheService to be more abstract 

5. Proper timeout handling when calling the supplier REST API end point. 

6. Configure CrossOrigin

7. cover corner cases with the unit tests 
 
