package com.sb.kafkaproducer.kafkaproducer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
// String KAFKA producer service
public class KafkaStrPrdService {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger("KafkaStrPrdService");
    public void sendMessage(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("hellotest", dtf.format(now).toString(), message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.info("Failed to send your message to HELLOTEST topic");
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Successfully sent message over to HELLOTEST topic");
            }
        });
    }
}
