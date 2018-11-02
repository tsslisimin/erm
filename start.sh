#!/bin/bash
if [ "" == "$JAVA_HOME" ]; then
    echo "Use default JDK Path."
    JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64
fi

cd /data/erm

git pull

curl -X POST http://localhost:8080/shutdown

java_bin=$JAVA_HOME/bin/java
server_home=.
log_home=$server_home/logs/coomia/erm
log_out=$log_home/stdout.log
log_err=$log_home/stderr.log

if [ ! -d $log_home ]; then
  mkdir -p $log_home
fi

mvn spring-boot:run 1>>$log_out 2>>$log_err & 
echo $! > $server_home/erm.pid