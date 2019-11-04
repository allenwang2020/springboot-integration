#!/bin/bash
#source ~/.bash_profile
cd /opt/zookeeper/apache-zookeeper-$ZOOKEEPER_VERSION-bin
cp conf/zoo_sample.cfg conf/zoo.cfg
sed -i 's/.*dataDir=.*$/dataDir=\/data\/zookeeper/g' conf/zoo.cfg
mkdir -p /logs/zookeeper && sed -i '/.*dataDir=.*$/i dataLogDir=\/logs\/zookeeper' conf/zoo.cfg
[ ! -z $ZOOKEEPER_PORT ] && sed -i 's/.*clientPort=.*$/clientPort='$ZOOKEEPER_PORT'/g' conf/zoo.cfg
[ ! -z $ZOOKEEPER_ID ] && mkdir -p /data/zookeeper && echo $ZOOKEEPER_ID > /data/zookeeper/myid
[ ! -z $ZOOKEEPER_SERVERS ] && array=(${ZOOKEEPER_SERVERS//,/ }) && for server in ${array[@]}; do echo $server >> conf/zoo.cfg; done
bin/zkServer.sh start-foreground