mvn -f service/pom.xml clean package -DskipTests
java -jar -Dspring.profiles.active=prod service/target/ved-0.0.1-SNAPSHOT.jar