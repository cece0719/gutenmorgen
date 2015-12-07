#!/bin/sh

NOW_TIME=`date +%Y%m%d%H%M%S`

#gitclone
git clone https://github.com/cece0719/gutenmorgen.git /home/webservice/apps/gutenmorgen/clone_$NOW_TIME
#/gitclone

#tomcat shutdown
PID=`ps -ef | grep "\/apps\/gutenmorgen\/" | grep -v grep | awk '{print $2}'`
CNT=1
curl -X POST http://127.0.0.1:8081/shutdown
while [ "x$PID" != "x" ] ; do
        echo "Process Still exist. $PID"
        if [ $CNT -ge "5" ] ; then
                echo "Send Kill Signal Tomcat process $PID"
                kill -9 $PID
        fi
        echo "Waiting More..."
        let CNT=$CNT+1
        sleep 2
        PID=`ps -ef | grep "\/apps\/gutenmorgen\/" | grep -v grep | awk '{print $2}'`
done
echo "Stop Sucessfully"
#/tomcat shutdown

#link
rm /home/webservice/apps/gutenmorgen/current
ln -s /home/webservice/apps/gutenmorgen/clone_$NOW_TIME /home/webservice/apps/gutenmorgen/current

ls -d -1 /home/webservice/apps/gutenmorgen/** | grep 'clone_' | head -n -3 | xargs rm -rf
#/-link

#tomcat startup
cd /home/webservice/apps/gutenmorgen/current/
/home/webservice/apps/gutenmorgen/current/gradlew bootRun -Pprofile=real
#/tomcat startup
