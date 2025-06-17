package org.vse.shop.kafka;

public interface MessageHandler<T> {
    void onMessage(T message);
}
