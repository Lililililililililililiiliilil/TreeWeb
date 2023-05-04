FROM openjdk:19
ADD target/myTree-1.0-SNAPSHOT.jar myTree-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "myTree-1.0-SNAPSHOT.jar"]
EXPOSE 8080

