#!/bin/sh

TOMCATDIRNAME=tomcat
TOMCATDIR=$HOME/apps/$TOMCATDIRNAME
SCRIPT=$(readlink -f "$0")
WORKSPACE=$(dirname "$SCRIPT")

#gitpull
git --work-tree=/home/webservice/workspace/gutenmorgen --git-dir=/home/webservice/workspace/gutenmorgen/.git pull
#/gitpull

#tomcat shutdown
$TOMCATDIR/bin/shutdown.sh
PID=`ps -ef | grep "\/$TOMCATDIRNAME\/" | grep -v grep | awk '{print $2}'`
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
        PID=`ps -ef | grep "\/$TOMCATDIRNAME\/" | grep -v grep | awk '{print $2}'`
done
echo "Stop Sucessfully"
#/tomcat shutdown

#build
gradle clean war -p /home/webservice/workspace/gutenmorgena/ -Pprofile=real >/dev/null &
#/build

#tomcat startup
$TOMCATDIR/bin/startup.sh
#/tomcat startup
