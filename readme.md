Areas of improvements 
-  
1. If Jobs and Workers in production are actually provided as a huge JSON payload, it should be parsed via a streaming API ( e.g. jackson streaming ).

2. Jobs and Workers need to be cached and refreshed. Cache expiry period should make sense from a business perspective. 
Needless to say that expiry period duration will be impacted if the supplier API utilizes HTTP cache-control headers. 

3. If payload is large, parallelize the matching algorithm.

4. reWrite ProviderDataCacheService to be more abstract 

5. Proper timeout handling when calling the supplier REST API endpoint. 

6. Configure CrossOrigin

7. cover corner cases with unit tests
