FROM openjdk:8-jre-alpine

MAINTAINER Poggi Giampiero '<giampiero.poggi@spindox.it>'

ARG APP_NAME=sudoku

COPY ./target/${APP_NAME}.jar /usr/${APP_NAME}/

RUN chgrp -R 0 /usr/${APP_NAME}/ && \
    chmod -R g=rwx /usr/${APP_NAME}/

EXPOSE 8091

USER 1001

WORKDIR /usr/${APP_NAME}/

CMD ["java", "-jar", "sudoku.jar"]