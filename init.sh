#!/bin/bash


rm -rf rest/target/
cd rest
mvn package
cd ..

docker-compose up --build

