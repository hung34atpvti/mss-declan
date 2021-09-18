FROM adoptopenjdk/openjdk11
RUN echo "${JAR_FILE}"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# COPY ${BOOTSTRAP_YML} bootstrap.yml
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=docker","/app.jar"]
# ENTRYPOINT ["java","-jar","-Dspring.cloud.bootstrap.location=/bootstrap.yml","/app.jar"]