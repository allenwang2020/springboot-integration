#FROM ubuntu:19.04

#RUN apt-get update &&\
#    apt-get install -y vim wget

#RUN mkdir /opt/java 

#COPY env/lib/jdk-8u202-linux-x64.tar.gz /opt/java/	

#RUN tar -zxf /opt/java/jdk-8u202-linux-x64.tar.gz -C /opt/java &&\
#	rm /opt/java/jdk-8u202-linux-x64.tar.gz &&\
#	touch ~/.bash_profile &&\
#    echo 'export PATH=$PATH' >> ~/.bash_profile &&\
#	sed -i "/^export PATH/i export JAVA_HOME=/opt/java/jdk1.8.0_202" ~/.bash_profile &&\
#	sed -i  's/^export PATH.*$/&:\$JAVA_HOME\/bin/g' ~/.bash_profile


#RUN source ~/.bash_profile
#ENV JAVA_HOME /opt/java/jdk1.8.0_202
#ENV PATH $PATH:$JAVA_HOME/bin
#RUN export JAVA_HOME
#RUN export PATH

FROM openjdk:8-jdk

ENV ZOOKEEPER_VERSION "3.5.6"


RUN mkdir /opt/zookeeper &&\
	wget https://www-eu.apache.org/dist/zookeeper/zookeeper-$ZOOKEEPER_VERSION/apache-zookeeper-$ZOOKEEPER_VERSION-bin.tar.gz -P /opt/zookeeper &&\
    tar -zxf /opt/zookeeper/apache-zookeeper-$ZOOKEEPER_VERSION-bin.tar.gz -C /opt/zookeeper &&\
	rm /opt/zookeeper/apache-zookeeper-$ZOOKEEPER_VERSION-bin.tar.gz	

COPY scripts/zk-start.sh /opt/zookeeper/

EXPOSE 2181 

ENTRYPOINT ["bash", "/opt/zookeeper/zk-start.sh"]
