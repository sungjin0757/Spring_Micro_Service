# Spring_Micro_Service -A Kind Of ShoppingMall Application Without Web Browser
### OUTLINE
**Simple Application Using Micro Service Architecture**</br>

### DEVELOP SPEC.
- Spring Boot
- Spring Cloud Netflix Eureka 
- Spring Cloud Gateway
- JPA
- Spring Security(Scheduled,Not Yet)
- RestApi
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
- 2021/06/03 Create Some Of UserService
	- vo For Client Request
	- Repository
	- Service
	- Controller
	- domain
- 2021/06/04 Add Spring Security In UserService
- 2021/06/06 Modify UserService & gateway
	- gateway-service : Enroll USER-SERVICE for Routing
	- USER-SERVICE : ADD Find Service
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
- User-Service(Only Using REST API Without Web Browser)
	- RequestMapping("/user-service/")
	- Function
		- health_check
			- GetMapping("/health_check")
			- return Present Port Number
		- welcome
			- GetMapping("/welcome")
			- return Greeting Message(Welcome to the Simple Ecommerce.)
		- Join(회원 가입)
			- PostMapping("/users")
			- Client Request Body Is Converted To UserDto By Using ModelMapper
				- UserDto Is Used For UserService
			- UserDto Is Converted To UserEntity Using ModelMapper
				- UserEntity Is Used For UserRepository
			- Spring Security For Password Encode
				- BcryptPasswordEncode
			- Spring Security For Authorization
		- Find(회원 조회)
			- findAll()
				- GetMapping("/users")
			- findByUserId(userId)
				- GetMapping("/users/{userId}")