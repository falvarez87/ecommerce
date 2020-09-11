# CentOS 7
FROM centos:7
LABEL maintainer="Fabian Alvarez, falvarezh87@gmail.com"

ENV JAVA_VER  11
ENV JAVA_HOME /opt/jdk-$JAVA_VER/
ENV GROUP_ID=com.falvarez
ENV ARTIFACT_ID=marketplace

# Install Packages
RUN yum update -y; \
    yum install -y wget unzip curl vim python-setuptools sudo; \
    easy_install supervisor
RUN wget https://download.java.net/openjdk/jdk${JAVA_VER}/ri/openjdk-${JAVA_VER}+28_linux-x64_bin.tar.gz -O /opt/jdk.tar.gz

RUN cd /opt; \
    tar -xvf jdk.tar.gz; \
    rm jdk.tar.gz

RUN cd /opt/jdk-$JAVA_VER; \
    alternatives --install /usr/bin/java java /opt/jdk-$JAVA_VER/bin/java 2
RUN yum clean all

COPY target/${ARTIFACT_ID}-*.jar /${ARTIFACT_ID}.jar

EXPOSE 8080

CMD java -jar /${ARTIFACT_ID}.jar