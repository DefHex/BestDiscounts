FROM openjdk:24
EXPOSE 8080
ADD backend/target/BestDiscounts.jar BestDiscounts.jar
ENTRYPOINT ["java", "-jar", "BestDiscounts.jar"]