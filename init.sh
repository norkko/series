#!/bin/bash

rm -rf rest/target/
./mvnw -f ./rest -DskipTests package
docker-compose up --build