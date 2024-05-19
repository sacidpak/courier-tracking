## Requirements
- Java 21
- Maven

## Running the Application
1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn clean install`.
4. Run `mvn spring-boot:run`.

## Sample Request
1. Go to http://localhost:8080/swagger-ui/index.html in browser
2. Find `POST /api/couriers/location`
3. Send this request
 ```json
{
  "courierId": "55",
  "latitude": 40.9923307,
  "longitude": 29.1244229
} 
 ```
4. Send this request 
 ```json
{
"courierId": "55",
"latitude": 40.9923307,
"longitude": 29.1304229
}
 ```
5. Go to http://localhost:8080/h2-console in browser
6. Auth info h2 db
 ```
jdbc url: jdbc:h2:~/test
username: sa
password: sa
 ```
7. Run this scripts
 ```
   select * from courier_distance;
   select * from courier_location;
   select * from courier_store_log;
 ```
8. Check records
9. Find `GET /api/couriers/total-distance` in swagger
10. Set courierId = 55 and execute
11. You will see this response
 ```
{
  "courierId": "55",
  "totalDistance": 0.729
}
 ```