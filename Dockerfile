FROM openjdk:14-alpine
COPY build/libs/stacksherpa-core-*-all.jar stacksherpa-core.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "stacksherpa-core.jar"]