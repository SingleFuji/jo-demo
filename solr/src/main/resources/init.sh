#!/bin/sh
SHELL_PROG=./init.sh
DIR="${BASH_SOURCE-$0}"
DIR=`dirname ${BASH_SOURCE-$0}`
ROOT_HOME=$DIR;export ROOT_HOME
APP_HOME=$DIR/resources/;export APP_HOME
DATA_HOME=$DIR/data/;export DATA_HOME
LIB_HOME=$DIR/lib;export LIB_HOME

PROJECT_NAME=cardmanage
PROCESS_NAME=cardmanage
##后台启动##
PROCESS_MAIN=com.jo.demo.ServerLauncher
JAVA_HOME=/usr/java/jdk1.7.0_75
JRE_HOME=/usr/java/jdk1.7.0_75/jre
PROCESS_PATH=$ROOT_HOME
CLASSPATH=$JAVA_HOME/lib/*:$PROCESS_PATH/resources/:$PROCESS_PATH/config/:$PROCESS_PATH/lib/*

export PROJECT_NAME
export PROCESS_NAME
export PROCESS_PATH
export JAVA_HOME
export JRE_HOME
export CLASSPATH
export LC_ALL=zh_CN.GBK
export MAINPROG
#ulimit -n 102297

#process name, need to change
MAINPROG=$PROCESS_MAIN;export MAINPROG

#java -Xdebug -Xrunjdwp:transport=dt_socket,address=6777,server=y,suspend=y -Xms800m -Xmx800m -Xmn256m -Xss256k  -classpath $CLASSPATH $MAINPROG
#java  -Xms800m -Xmx800m -Xmn256m -Xss256k  -classpath $CLASSPATH $MAINPROG

start() {
        echo "[`date`] Begin starting $MAINPROG ... "
        ##java -Xms${f.memory.Xms} -Xmx${f.memory.Xmx} -Xss${f.memory.Xss} -XX:SurvivorRatio=8 -XX:+UseParNewGC -XX:ParallelGCThreads=6 -XX:+CMSParallelRemarkEnabled -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=50 -XX:+UseCMSInitiatingOccupancyOnly -XX:MaxTenuringThreshold=64 -XX:+PrintGCDetails -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCTimeStamps -Xloggc:gc.log  -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=3  -classpath $CLASSPATH $MAINPROG &
		#nohup java -Xms100m -Xmx100m -Xss256k -classpath $CLASSPATH $MAINPROG >/dev/null 2>&1 &
	 	nohup	 java -Xms1024m -Xmx2048m -XX:PermSize=128m -Xmn512m -Xss512k -classpath $CLASSPATH $MAINPROG  >/dev/null 2>&1 &
        if [ $? -eq 0 ]
        then
			echo "[`date`] Startup $MAINPROG success."
			return 0
        else
			echo "[`date`] Startup $MAINPROG fail."
			return 1
        fi
}

debug() {
        echo "[`date`] Begin starting $MAINPROG... "
        java -Xdebug -Xrunjdwp:transport=dt_socket,address=6777,server=y,suspend=y -Xms10000m -Xmx10000m -Xmn256m -Xss256k  -classpath $CLASSPATH $MAINPROG &
        if [ $? -eq 0 ]
        then
			echo "[`date`] Startup $MAINPROG success."
			return 0
        else
			echo "[`date`] Startup $MAINPROG fail."
			return 1
        fi
}

stop() {
    echo "[`date`] Begin stop $MAINPROG... "
    PROGID=`ps -ef|grep "$MAINPROG"|grep -v "grep"|sed -n '1p'|awk '{print $2" "$3}'`
	if [ -z "$PROGID" ]
	then
		echo "[`date`] Stop $MAINPROG fail, service is not exist."
		return 1
	fi
	
    kill -9 $PROGID
    if [ $? -eq 0 ]
    then
		echo "[`date`] Stop $MAINPROG success."
		return 0
    else
		echo "[`date`] Stop $MAINPROG fail."
		return 1
    fi
}


case "$1" in
start)
  start
  exit $?
  ;;
stop)
  stop
  exit $?
  ;;
restart)
  stop
  start
  exit $?
  ;;
debug)
  debug
  exit $?
  ;;
*)
  echo "[`date`] Usage: $SHELL_PROG {start|debug|stop|restart}"
  exit 1
  ;;
esac

