# 基础镜像，这里其实省略了register的地址
FROM java:8
# author信息
MAINTAINER tongjian
EXPOSE 9999
ADD target/com.example.spring.docker-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]