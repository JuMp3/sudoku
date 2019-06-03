FROM openjdk:8-jre-alpine
MAINTAINER Giampiero Poggi

EXPOSE 8091

RUN mkdir -p /Logs
RUN mvn clean install -Dmaven.test.skip=true

VOLUME ["/Logs"]

USER 1001

CMD ["java","-jar","-Xmx96m","-Xss512k","sudoku.jar"]
