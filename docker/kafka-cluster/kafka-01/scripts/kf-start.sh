#!/bin/bash
#source ~/.bash_profile
cd /opt/kafka/kafka_2.11-$KAFKA_VERSION
[ ! -z $ZOOKEEPER_CONNECT ] && sed -i 's/.*zookeeper.connect=.*$/zookeeper.connect='$ZOOKEEPER_CONNECT'/g'  config/server.properties
[ ! -z $BROKER_ID ] && sed -i 's/broker.id=.*$/broker.id='$BROKER_ID'/g'  config/server.properties
[ ! -z $LISTENERS ] && sed -i 's/#listeners=.*$/'$LISTENERS'/g'  config/server.properties
[ ! -z $ADVERTISED_LISTENERS ] && sed -i 's/#advertised.listeners=.*$/'$ADVERTISED_LISTENERS'/g' config/server.properties
bin/kafka-server-start.sh config/server.properties