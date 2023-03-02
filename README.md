## Example of how to configure Spring Boot application with Kafka in docker

### `It is still under development`

### Description:
This project has example of producer and consumer for Kafka messages using Spring framework      
It has examples of how to:      
-- write docker-compose yml file doe zookeeper and kafka dockers  
-- bash script to manage docker containers by one command  
-- send a message to the Kafka topic  
-- consume the message from the kafka topic  
-- configure application with the Spring framework  

### Preconditions:
-- AdoptOpenJDK 11  
-- Maven 3.8.x  
-- docker and docker-compose

### Run the project:
-- use docker/skd-containers.sh script to run zookeeper and kafka dockers `./skd-containers.sh -c up -zk`  
-- compile the code with `mvn clean install`  
-- run Spring Boot application with `mvn spring-boot:run`
