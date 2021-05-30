# Spring_Micro_Service
### OUTLINE
**Simple Application Using Micro Service Architecture**</br>

### DEVELOP SPEC.
- Spring Boot
- Spring Cloud Netflix Eureka 
- Spring Cloud Gateway
- JPA(Scheduled,Not Yet)
- Spring Security(Scheduled,Not Yet)
- RestApi(Scheduled,Not Yet)
- Git(Scheduled,Not Yet)
- Spring Cloud Config(Scheduled,Not Yet)
- RabbitMQ : Spring Cloud Bus(Scheduled,Not Yet)
- Kafka(Scheduled,Not Yet)
- ZipKin(Scheduled,Not Yet)
- Spring Cloud Sleuth(Scheduled,Not Yet)
- Docker(Scheduled,Not Yet)
</br>

### DEVELOP LOG
- 2021/05/22 Add ServiceDiscovery Using Spring Cloud Netflix Eureka
	- discoveryservice : Eureka Server
	- userservice : Eureka Client
- 2021/05/25 Add Netflix_Zuul for API Gateway
	- First Service : Test
	- Second Service for : Test
	- zuulservice  : API Gateway
- 2021/05/27 Add Spring Cloud Gateway
	- gateway-service : Filter Test Using Java & yml, Add Custom Filter(Pre Filter, Post Filter)
- 2021/05/30 Modify Spring cloud Gateway
	- gateway-service : Add Global Filter, Logging Filter
- 2021/05/31 Modify Spring cloud Gateway
	- gateway-service : Modify application.yml For Enrolling in Eureka Server 
	- first-service : Modify application.yml For Enrolling in Eureka Server 
	- second-service : Modify application.yml For Enrolling in Eureka Server 
</br>

### ISSUE
- Eureka Server For ServiceDiscovery
	- Load Balancer
- Spring Cloud Gateway For API GateWay
	- Proxy Request & Response
	- Filtering Request & Response
		- Custom Filter
			- Pre Filter : Log Request_ID
			- Post Filter : Log Respons_status
		- Global Filter
			- Pre Filter : Log Request_ID
			- Post Filter : Log Respons_status
		- Logging Filter
			- Pre Filter : Log Request_ID
			- Post Filter : Log Respons_status