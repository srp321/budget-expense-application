FROM openjdk:11
COPY target/budget-assignment-1.0.0.jar budget-assignment-1.0.0.jar
ENTRYPOINT ["java","-jar","/budget-assignment-1.0.0.jar"]
