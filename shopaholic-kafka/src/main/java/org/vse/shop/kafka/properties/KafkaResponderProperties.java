package org.vse.shop.kafka.properties;

import java.util.Map;

public class KafkaResponderProperties {
    private String topic;
    private Map<String, Object> producer;

    public Map<String, Object> getProducer() {
        return producer;
    }

    public void setProducer(Map<String, Object> producer) {
        this.producer = producer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
