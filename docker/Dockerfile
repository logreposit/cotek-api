FROM openjdk:11-jre

MAINTAINER Dominic Miglar <dominic.miglar@netunix.at>

WORKDIR /opt/logreposit/cotek-api-service

COPY run.sh    /opt/logreposit/cotek-api-service/run.sh
COPY app.jar   /opt/logreposit/cotek-api-service/app.jar
RUN  chmod 755 /opt/logreposit/cotek-api-service/run.sh

ENTRYPOINT ["/opt/logreposit/cotek-api-service/run.sh"]
