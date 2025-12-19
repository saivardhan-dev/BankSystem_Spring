# BANK APPLICATION
This application is basically 3 layers:
```text
Client (Postman / Browser)
        |
        v
Controller (BankController)   <-- receives HTTP requests (/api/...)
        |
        v
Service (BankService)         <-- contains business logic (add, deposit, withdraw)
        |
        v
Model (User)                  <-- data object (accountNumber, name, balance)
```
Spring Boot starts a server (Tomcat) on port 8080. When I hit an endpoint like:
```text
API Endpoints: 
- Home            GET /api/
- Create User     POST /api/users
- Request Body    {
                     "accountNumber": 101,
                     "name": "Vardhan",
                     "balance": 5000
                  }
- Get User by     GET /api/users/{accountNumber}
Account Number
- Get Account     GET /api/users/{accountNumber}/balance
Balance
- Deposit Amount  POST /api/users/{accountNumber}/deposit?amount=1000
- Withdraw Amount POST /api/users/{accountNumber}/withdraw?amount=500
```  
…Spring routes that request to the correct method in our BankController.

## 1. What Happens when I runs the application? 
Step by step flow
- JVM Starts execution from main(), Runs BankApiApplication.main()
- SpringApplication.run(...) starts Spring Boot application
  
Spring Boot:
- Creates the Spring Container
- Scans packages for components (@RestController, @Service, etc.)
- Creates and wires all beans
- Starts the embedded Tomcat server (default on port 8080)
- scans your package for Spring components (beans)
- Before any request, Spring Boot Initializes Beans
- creates bean objects like BankController and BankService
- injects dependencies (DI)
- 
Then our Application server is ready to receive requests.

## 2. What each class is doing
### User class (Model / POJO)
 
 This is just a data holder:
 - accountNumber
 - name
 - balance
with getters/setters.

Spring uses it to automatically convert JSON ↔ Java object.

Example:
```text
{ "accountNumber": 1001, "name": "sam", "balance": 1234.567 }
```
### BankService class (Business logic layer)
This is responsible for:

- reading HTTP request data (path variables, query params, body)
- calling service methods
- returning HTTP responses (200, 400, 404)

Example:
```text
POST /api/users/101/deposit?amount=50
Flow:
	1.	Controller reads accountNumber=101 and amount=50
	2.	Calls bank.deposit(101, 50)
	3.	Returns new balance in response
```
### BankApplication (Spring Boot entry point)
This is the main class that must have:

- @SpringBootApplication
- and be in a top package so component scanning finds everything

## 3. Request flow diagram (what happens “inside” Spring)
```text
HTTP Request
   |
   v
Tomcat (8080)
   |
   v
DispatcherServlet  (Spring MVC front controller)
   |
   v
Find matching @GetMapping/@PostMapping
   |
   v
Call controller method
   |
   v
Controller calls BankService
   |
   v
Return ResponseEntity / JSON
   |
   v
HTTP Response to client
```

