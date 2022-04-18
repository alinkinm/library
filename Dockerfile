FROM openjdk:8
ADD target/library-0.0.1-SNAPSHOT.jar library-0.0.1-SNAPSHOT.jar
EXPOSE 9966
ENTRYPOINT ["java", "-jar", "library-0.0.1-SNAPSHOT.jar"]