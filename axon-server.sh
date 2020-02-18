#!/usr/bin/env bash

### Functions

axon_server_start() {
  docker run --rm -d --name my-axon-server -p 8024:8024 -p 8124:8124 --hostname axonserver -e AXONSERVER_HOSTNAME=axonserver axoniq/axonserver
}

axon_server_stop() {
  docker stop my-axon-server
}

usage() {
  echo "USAGE: ./axon-server.sh [COMMAND]"
  echo ""
  echo "  start : starts AxonServer (connect to http://localhost:8024)"
  echo "  stop  : stops AxonServer (cache will be removed)"
}

### Main

if [ "$1" != "" ]; then
  case $1 in
    start)
      echo "Starting AxonServer"
      axon_server_start
      ;;

    restart)
      echo "Restarting AxonServer"
      axon_server_stop
      axon_server_start
      ;;

    stop)
      echo "Stopping AxonServer"
      axon_server_stop
      ;;

    *)
      echo "Invalid COMMAND: $1"
      usage
      exit 1
      ;;
  esac
else
  usage
fi

