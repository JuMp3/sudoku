FROM stakater/java-centos:7-1.8

MAINTAINER Giampiero Poggi
ARG MAVEN_VERSION=3.5.4
EXPOSE 8091

# Install required tools
# which: otherwise 'mvn version' prints '/usr/share/maven/bin/mvn: line 93: which: command not found'
RUN yum update -y && \
  yum install -y which && \
  yum clean all

# Maven
RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
  && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_VERSION=${MAVEN_VERSION}
ENV M2_HOME /usr/share/maven
ENV maven.home $M2_HOME
ENV M2 $M2_HOME/bin
ENV PATH $M2:$PATH

RUN mkdir -p /Logs
RUN mvn clean install -Dmaven.test.skip=true

VOLUME ["/Logs"]

USER 1001

CMD ["java","-jar","-Xmx96m","-Xss512k","sudoku.jar"]
