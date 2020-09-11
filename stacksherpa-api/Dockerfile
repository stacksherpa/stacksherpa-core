FROM openjdk:14-alpine
COPY target/stacksherpa-api-*.jar stacksherpa-api.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "stacksherpa-api.jar"]