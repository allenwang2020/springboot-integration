FROM openjdk:8-jdk


ENV KAFKA_VERSION "2.3.1"


RUN mkdir /opt/kafka &&\
	wget https://www-eu.apache.org/dist/kafka/$KAFKA_VERSION/kafka_2.11-$KAFKA_VERSION.tgz -P /opt/kafka &&\
	tar -zxf /opt/kafka/kafka_2.11-$KAFKA_VERSION.tgz -C /opt/kafka/ &&\
	rm /opt/kafka/kafka_2.11-$KAFKA_VERSION.tgz &&\
	sed -i 's/num.partitions.*$/num.partitions=3/g' /opt/kafka/kafka_2.11-$KAFKA_VERSION/config/server.properties

COPY scripts/kf-start.sh /opt/kafka

EXPOSE 9092

ENTRYPOINT ["bash", "/opt/kafka/kf-start.sh"]