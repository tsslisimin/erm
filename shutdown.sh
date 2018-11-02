#!/bin/bash
if [ "" == "$JAVA_HOME" ]; then
    echo "Use default JDK Path."
    JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64
fi

cd /data/erm

curl -X POST http://localhost:8080/shutdown