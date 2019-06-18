#!/bin/bash

cd rest
rm -rf target/
mvn package
cd ..
docker-compose up --build

