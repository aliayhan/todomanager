#!/usr/bin/env bash

# Start script. Docker compose seems to not support parameters of line 13/14, so we stay at bash scripting.

docker run --name todomanagerdb \
    -v /$(PWD)/src/main/schema:/docker-entrypoint-initdb.d \
    -e MYSQL_ROOT_PASSWORD=admin \
    -d mysql:5.7.16
docker run --name todomanager \
    --link todomanagerdb:mysql \
    -p8080:8080 \
    -d aliayhan/todomanager:1.0.0 \
    -h"$MYSQL_PORT_3306_TCP_ADDR" \
    -P"$MYSQL_PORT_3306_TCP_PORT"