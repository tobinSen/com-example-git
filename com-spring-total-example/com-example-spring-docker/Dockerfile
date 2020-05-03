FROM java:8
MAINTAINER tongjian
EXPOSE 9999
ADD target/com.example.spring.docker-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]