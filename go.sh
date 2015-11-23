#!/bin/bash

git pull

TOMCATDIR=/home/webservice/apps/tomcat

mvn clean compile war:exploded

$TOMCATDIR/bin/shutdown.sh

PID=`ps -ef | grep "\/tomcat\/" | grep -v grep | awk '{print $2}'`

CNT=1
while [ "x$PID" != "x" ] ; do
        echo "Process Still exist. $PID"
        if [ $CNT -ge "5" ] ; then
                echo "Send Kill Signal Tomcat process $PID"
                kill -9 $PID
        fi
        echo "Waiting More..."
        let CNT=$CNT+1
        sleep 2
        PID=`ps -ef | grep "\/tomcat\/" | grep -v grep | awk '{print $2}'`
done
echo "Stop Sucessfully"

$TOMCATDIR/bin/startup.sh