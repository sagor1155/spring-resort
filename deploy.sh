#!/bin/bash

DIR=CS544%20-%20Resort%20management%20system
REPO=git@ssh.dev.azure.com:v3/avanvu0225/CS544%20-%20Resort%20management%20system/CS544%20-%20Resort%20management%20system

echo "----------------------------------------------------"
echo "[SCRIPT] REMOVE OLD SOURCE CODE"
echo "----------------------------------------------------"
rm -rf $DIR || echo "The directory does not exist."
echo ""

echo "----------------------------------------------------"
echo "[SCRIPT] CLONE REPOSITORY"
echo "----------------------------------------------------"
git clone $REPO
echo ""

cd $DIR

echo "----------------------------------------------------"
echo "[SCRIPT] MAVEN BUILD"
echo "----------------------------------------------------"
mvn clean install -DskipTests
echo ""

echo "----------------------------------------------------"
echo "[SCRIPT] DOCKER BUILD"
echo "----------------------------------------------------"
docker build -t ea/resort-management-system .
echo ""

echo "----------------------------------------------------"
echo "[SCRIPT] DOCKER RUN"
echo "----------------------------------------------------"
docker-compose up
echo ""