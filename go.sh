#!/bin/sh

TOMCATDIRNAME=tomcat
TOMCATDIR=$HOME/apps/$TOMCATDIRNAME
SCRIPT=$(readlink -f "$0")
WORKSPACE=$(dirname "$SCRIPT")
NOW_TIME=`date +%Y%m%d%H%M%S`

#gitclone
git clone https://github.com/cece0719/gutenmorgen.git /home/webservice/workspace/gutenmorgen_$NOW_TIME
#/gitclone

#build
gradle clean war -p /home/webservice/workspace/gutenmorgen_$NOW_TIME/ -Pprofile=real
#/build

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

#link
rm /home/webservice/workspace/gutenmorgen
ln -s /home/webservice/workspace/gutenmorgen_$NOW_TIME /home/webservice/workspace/gutenmorgen

ls /home/webservice/workspace | grep gutenmorgen_ | head -n -3 | xargs rm -rf
#.link

#tomcat startup
$TOMCATDIR/bin/startup.sh
#/tomcat startup
