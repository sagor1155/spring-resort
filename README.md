# Resort Management System

## Technologies
- Spring Boot
- MySQL
- JDK
- Maven
- Docker
- AWS EC2

## Security
### Login/authenticate
API Endpoint:
```
POST http://localhost:8080/users/authenticate
```
BODY:
```json
{
   "username": "dhiman",
   "password": "dhiman"
}
```
RESPONSE:

```json
{
   "success": true,
   "message": "Token generated successfully",
   "data": "token-string"
}
```

Use the token string as access token for all secured API's. 

### Secure your endpoints
#### Method Level Security
Annotate your API endpoints with `@PreAuthorize` and specify roles. 

`UserController.java`
```java
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> admin() {...}

    @GetMapping("/admin-client")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT')")
    public ResponseEntity<ResponseDto> adminAndClient() {...}
```

<b>OR,</b>

#### Global Security
Permit or, authenticate endpoints with `requestMatchers` inside `SecurityFilterChain` bean. 

`SecurityConfig.java`
```java
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/users/authenticate",
                                "/users/add",
                                "/users/ping"
                        ).permitAll()
                        .requestMatchers("/users/admin").hasAuthority("ADMIN")
                        .requestMatchers("/users/client").hasAnyAuthority("ADMIN", "CLIENT")
                        .requestMatchers("/users/**").authenticated()
                )
                .build();
    }

```

### Refs:
- https://www.youtube.com/watch?v=R76S0tfv36w
- https://www.youtube.com/watch?v=NcLtLZqGu2M
- https://github.com/Java-Techie-jt/spring-boot3-jwt

## Run project in Docker
1. Go to project directory
2. Build project:
   ` mvn clean install -DskipTests`
3. Create docker image:
   `docker build -t ea/resort-management-system .`
4. Create and run all the containers using docker-compose file in the project root directory:
   `docker-compose up`

## Deploy on AWS EC2
### Launch EC2 Instance
- Create VPC
- Create Key pair and add your ssh public key
- Create Security group 
  - Inbound rules: 
    - Port 22 for SSH
    - Port 8080 for Spring Boot application
  - Outbound rules: allow all traffic
- Create EC2 instance 

### Install necessary applications on EC2
- Install - `jdk-17`, `maven`, `docker`, `docker-compose`, `git`
- Refs: 
  - https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/amazon-linux-install.html
  - https://docs.aws.amazon.com/neptune/latest/userguide/iam-auth-connect-prerq.html
  - https://www.cyberciti.biz/faq/how-to-install-docker-on-amazon-linux-2/
  - https://gist.github.com/npearce/6f3c7826c7499587f00957fee62f8ee9

### Deploy application
- Copy the `deploy.sh` script onto EC2
  - From your machines terminal run the following command
    - `scp deploy.sh ec2-user@public-ip-of-ec2:/home/ec2-user`
- SSH login to EC2
    - `ssh ec2-user@public-ip-of-ec2`
- Make the script executable
  - `chmod +x deploy.sh`
- Run the `deploy.sh` script
  - `./deploy`
- Enjoy!