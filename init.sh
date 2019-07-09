#!/bin/bash

if [ -d "rest/target" ]; then
    echo Already packaged
else
  ./mvnw -f ./rest -DskipTests package
  if [[ "$?" -ne 0 ]] ; then
    echo 'mvn package failed'; exit $rc
  fi
fi

docker-compose up --build