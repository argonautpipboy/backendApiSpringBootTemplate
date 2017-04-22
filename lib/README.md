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

