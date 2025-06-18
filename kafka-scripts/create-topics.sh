#!/bin/bash
echo "Creating Kafka topics..."
/opt/kafka/bin/kafka-topics.sh --create --if-not-exists \
  --bootstrap-server localhost:9092 \
  --topic order_push --partitions 4 --replication-factor 1

/opt/kafka/bin/kafka-topics.sh --create --if-not-exists \
  --bootstrap-server localhost:9092 \
  --topic pay_request --partitions 4 --replication-factor 1

/opt/kafka/bin/kafka-topics.sh --create --if-not-exists \
  --bootstrap-server localhost:9092 \
  --topic pay_response --partitions 4 --replication-factor 1

echo "Topics created"