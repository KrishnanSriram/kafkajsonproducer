package com.sb.kafkaproducer.kafkaproducer.service;

import com.sb.kafkaproducer.kafkaproducer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaCustomerPrdService {
    @Autowired
    KafkaTemplate<String, Customer> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger("KafkaStrPrdService");
    public void sendMessage(Customer c) {
        ListenableFuture<SendResult<String, Customer>> future = kafkaTemplate.send("hellocustomer", c.getCustomerId(), c);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Customer>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.info("Failed to send your message to HELLOCUSTOMER topic");
            }

            @Override
            public void onSuccess(SendResult<String, Customer> result) {
                logger.info("Successfully sent message over to HELLOCUSTOMER topic - " + c.getLastName());
            }
        });
    }
}
