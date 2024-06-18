FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ms-vehicle_repair.jar
ENTRYPOINT ["java","-jar","/ms-vehicle_repair.jar"]