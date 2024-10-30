package com.streaming.filme.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PessoaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Value("${kafka.topic.notify.pessoa}")
    private String topic;

    public void sendMessage(final String message) {
        this.kafkaTemplate.send(this.topic, message);
    }
}
