FROM openjdk:8-jdk
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Ho_Chi_Minh", "/app.jar"]