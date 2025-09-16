package com.xgaslan.stockservice.kafka;

import com.xgaslan.stockservice.constants.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumerService {

    @KafkaListener(
            id = "orderConsumerClient",
            topics = KafkaConstants.ORDER_TOPIC_NAME,
            groupId = KafkaConstants.GROUP_ID,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessage(Object message) {
        log.info("Consumed message: {}", message);
    }
}
