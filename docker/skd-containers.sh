#!/bin/bash

function f-help () {  
  echo " -c = docker command"
  echo " -zk = zookeeper"
  echo " -rem = stop and remove all docker containers, volumes, network"
  echo " "
  echo " "
  echo " EXAMPLES: "
  echo " "
  echo " stop all containers and remove docker volumes, containers"
  echo " ./skd-containers.sh -rem"
  echo " "
  echo " run docker command 'up --build -d' for the zookeeper and kafka containers"
  echo " ./skd-containers.sh -c up -zk"
  echo " "
  echo " run docker command 'stop' for the zookeeper and kafka containers"
  echo " ./skd-containers.sh -c down -zk"
  echo " "
  exit 0
}

function f-preparations() {
  if [[ $command == "up" ]]; then
    #command="up --build --force-recreate -d"
    command="up --build -d"
  fi
}

function f-common-params-run() {
  param="$command"
  echo "Command: " $1 $param
  $1 $param
}

if [[ "$1" == *"help"* || $# -eq 0 ]]; then
  f-help
fi

# Parameters
command=

# Docker compose files
zk_var='docker-compose -f ./zookeeper-kafka.yml'

# Check Flags
while test $# -gt 0; do
  case "$1" in
    -c)
      shift
      command=$1
      f-preparations
      shift
      ;;
    -zk)
      echo ' ** DOCKER ZOOKEEPER AND KAFKA'
      f-common-params-run "$zk_var"
      shift
      ;;
    -rem)
      echo ' ** REMOVE docker containers, volumes, networks '
      docker container stop $(docker container ls -aq)
      docker container rm $(docker container ls -aq)
      # yes Y | docker system prune -a --volumes
      yes Y | docker volume prune
      exit 1
      ;;
    *)
      echo -e " ERROR: unknown flag $1"
      exit 1
      ;;
  esac
done
echo " "
