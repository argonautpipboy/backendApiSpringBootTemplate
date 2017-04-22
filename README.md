###################
# PRE REQUISITES
###################

Having a JDK and maven repository configured

###################
# BUILD PROJECT
###################

### Compile projet using maven
Execute the following command on root project, note that it can also be run to lib and api repository independently.
api project depends on lib project, therefore, lib must be built first.

mvn clean install

### Run the project
mvn spring-boot:run

###################
# MONGODB
###################
Install mongodb for windows:
https://docs.mongodb.org/manual/tutorial/install-mongodb-on-windows/

run/execute mongod on you local environment or server.
Default mongodb uri is: mongodb://127.0.0.1:27017/apibackendspringboottemplate

The uri is configured in the application.properties file.

###################
# Swagger
###################
Use swagger to test out our apis

## Configuration
Add maven dependencies
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.2.2</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.2.2</version>
    <scope>compile</scope>
</dependency>

Enable swagger2 on the application
Add the annotation @EnableSwagger2 to the main class of the spring boot project (ApiBackEndApplication.java)
Add the following swagger configuration methods
@Bean
public Docket newsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName(SwaggerConstants.GROUP_NAME)
        .apiInfo(apiInfo())
        .select()
        .paths(PathSelectors.regex("/.*"))
        .build();
}

private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(SwaggerConstants.TITLE)
        .description(SwaggerConstants.DESCRIPTION)
        .contact(SwaggerConstants.CONTACT)
        .license(SwaggerConstants.LICENSE)
        .version(SwaggerConstants.VERSION)
        .build();
}

## Use Swagger
http://[SERVEUR]:[PORT]/swagger-ui.html#!/


###################
# JWT
###################
secret generation
node -e "console.log(require('crypto').randomBytes(32).toString('hex'));

