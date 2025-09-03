FROM openjdk:24
LABEL authors="Syuleyman"
EXPOSE 8080
ADD backend/target/BestDiscounts.jar BestDiscounts.jar
ENTRYPOINT ["java", "-jar", "BestDiscounts.jar"]
