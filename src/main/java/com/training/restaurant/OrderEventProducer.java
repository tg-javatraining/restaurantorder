package com.training.restaurant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class OrderEventProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void sendOrderEvent(OrderEvent orderEvent) throws JsonProcessingException {

        Integer key = orderEvent.getOrderEventId();
        String value = objectMapper.writeValueAsString(orderEvent);
        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {

                failure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                success(key, value, result);
            }
        });
    }

    private void success(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message has been sent successfully , key: {} and the value is {} and partition is {}",
                key, value, result.getRecordMetadata().partition());
    }

    private void failure(Integer key, String value, Throwable ex) {
        log.error("Error sending the message and exception is {} ", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in failure {},", throwable.getMessage());
        }
    }

}
