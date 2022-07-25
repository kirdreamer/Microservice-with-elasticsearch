#FROM openjdk:11
#COPY target/serverproject-0.0.1-SNAPSHOT.jar ./serverproject.jar
#CMD ["java", "-jar", "serverproject.jar"]
#
FROM maven:3.6.3-jdk-11
COPY ./ ./
RUN mvn clean package -DskipTests
CMD ["java", "-jar", "target/serverproject-0.0.1-SNAPSHOT.jar"]


#FROM maven:3.6.3-jdk-11
#COPY ./ ./
#RUN mvn clean package -DskipTests
#RUN docker run -d --name es762 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
#CMD ["java", "-jar", "target/serverproject-0.0.1-SNAPSHOT.jar"]