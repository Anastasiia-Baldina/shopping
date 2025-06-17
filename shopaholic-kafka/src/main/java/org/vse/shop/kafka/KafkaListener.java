package org.vse.shop.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vse.shop.kafka.properties.KafkaListenerProperties;
import org.vse.shop.kafka.utils.Json;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaListener<T> implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(KafkaListener.class);

    private final Consumer<String, String> kafka;
    private final String topic;
    private final ExecutorService scheduler;
    private final AtomicBoolean started = new AtomicBoolean();
    private final MessageHandler<T> handler;
    private final Class<T> messageType;

    public KafkaListener(KafkaListenerProperties cfg,
                         MessageHandler<T> handler,
                         Class<T> messageType) {
        this.topic = cfg.getTopic();
        this.handler = handler;
        this.messageType = messageType;
        Map<String, Object> props = new HashMap<>(cfg.getConsumer());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        this.kafka = new KafkaConsumer<>(props);
        started.set(true);
        scheduler = schedule(cfg.getDelayOnErrorMillis(), cfg.getPollIntervalMillis());
    }

    private ExecutorService schedule(long delayMillis, long pollIntervalMillis) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(
                () -> poolLoop(pollIntervalMillis), delayMillis, delayMillis, TimeUnit.MILLISECONDS);

        return scheduler;
    }

    private void poolLoop(long pollIntervalMillis) {
        try {
            kafka.subscribe(List.of(topic));
            Duration pollTimeout = Duration.ofMillis(pollIntervalMillis);
            while (started.get() && !Thread.currentThread().isInterrupted()) {
                var kafkaRecs = kafka.poll(pollTimeout);
                if (kafkaRecs.isEmpty()) {
                    continue;
                }
                List<T> data = new ArrayList<>(kafkaRecs.count());
                for (var rec : kafkaRecs) {
                    var key = rec.key();
                    var json = rec.value();
                    try {
                        log.info("Kafka fetch: key={}, msg={}", key, json);
                        data.add(Json.fromJson(json, messageType));
                    } catch (Exception e) {
                        log.error("Failed deserialize kafka record. p={},ofs={},msg={}",
                                rec.partition(), rec.offset(), json);
                    }
                }
                for (T msg : data) {
                    handler.onMessage(msg);
                }
                kafka.commitSync();
            }
        } catch (Exception e) {
            log.error("Error kafka pooling", e);
        }
    }

    @Override
    public void close() throws InterruptedException {
        if (started.compareAndSet(false, true)) {
            scheduler.shutdown();
            if (scheduler.awaitTermination(10_000, TimeUnit.SECONDS)) {
                scheduler.shutdown();
            }
        }
    }
}
