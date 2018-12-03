mvn clean package spring-boot:repackage -Dmaven.test.skip
nohup java -jar target/ls-wx-1.0-SNAPSHOT.jar >/dev/null &