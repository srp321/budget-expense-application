FROM maven:3.6-jdk-11 AS build
WORKDIR /budget-expense-application
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests clean package

FROM openjdk:11-jre-slim
COPY --from=build /budget-expense-application/target/budget-assignment-1.0.0.jar /budget-expense-application/budget-assignment-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "/budget-expense-application/budget-assignment-1.0.0.jar"]
