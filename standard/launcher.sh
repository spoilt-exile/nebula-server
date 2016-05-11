#!/bin/bash

NEBULA_PID_FILE='nebula.pid'
NEBULA_LOG_FILE='nebula.log'

start_application() {
  BUILD_ID=dontKillMe nohup java -jar nebula-server_static.jar &> $NEBULA_LOG_FILE 2>&1 & echo $! > $NEBULA_PID_FILE
}

kill_application() {
  kill -9 $(cat $NEBULA_PID_FILE)
  rm $NEBULA_PID_FILE
}

case "$1" in
  start)
    echo "Starting Nebula application service..."
    start_application
  ;;
  restart)
    echo "Restarting Nebula application service..."
    kill_application
    start_application
  ;;
  stop)
    echo "Stop Nebula application service..."
    kill_application
  ;;
esac