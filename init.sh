#!/bin/bash

rm -rf rest/target/
mvn -f ./rest package
docker-compose up --build