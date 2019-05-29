FROM openjdk:8-jre-alpine
MAINTAINER Giampiero Poggi
EXPOSE 8091
COPY maven /
VOLUME ["/docker_volume"]
ENTRYPOINT ["java","-jar","-Xmx96m","-Xss512k","sudoku.jar"]
