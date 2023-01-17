FROM lippert/adoptopenjdk-17

EXPOSE 8081

ADD target/TestContainers-0.0.1-SNAPSHOT.jar myapp.jar

ENTRYPOINT ["java", "-jar", "/myapp.jar"]