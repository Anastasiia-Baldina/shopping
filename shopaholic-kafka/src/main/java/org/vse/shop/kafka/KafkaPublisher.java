package org.vse.shop.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.vse.shop.kafka.properties.KafkaResponderProperties;
import org.vse.shop.kafka.utils.Json;
import ru.vse.shop.utils.Asserts;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class KafkaPublisher<T> {
    private final KafkaProducer<String, String> kafkaProducer;
    private final String topic;

    public KafkaPublisher(KafkaResponderProperties cfg) {
        this.topic = Asserts.notEmpty(cfg.getTopic(), "topic");
        Map<String, Object> props = new HashMap<>(cfg.getProducer());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        this.kafkaProducer = new KafkaProducer<>(props);
    }

    public void apply(String key, T payload) {
        var rec = new ProducerRecord<>(topic, key, Json.toJson(payload));
        try {
            kafkaProducer.send(rec).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
