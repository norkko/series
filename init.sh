#!/bin/bash

rm -rf rest/target/
mvn -f ./rest -DskipTests package
docker-compose up --build