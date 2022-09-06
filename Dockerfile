FROM openjdk:17-oracle
COPY target/*.jar BtcSubscriber.jar
ENTRYPOINT ["java","-jar","/BtcSubscriber.jar"]