#!/bin/bash
echo "Creating Kafka topics..."

kafka-topics --bootstrap-server kafka:9092 --create --topic order_push --partitions 4 --replication-factor 1
kafka-topics --bootstrap-server kafka:9092 --create --topic pay_request --partitions 4 --replication-factor 1
kafka-topics --bootstrap-server kafka:9092 --create --topic pay_response --partitions 4 --replication-factor 1

echo "Created topics:"
kafka-topics --bootstrap-server kafka:9092 --list