package com.xgaslan.stockservice.kafka;

import com.xgaslan.stockservice.constants.KafkaConstants;
import com.xgaslan.stockservice.dto.Order;
import com.xgaslan.stockservice.dto.OrderEvent;
import com.xgaslan.stockservice.entity.ProcessedOrder;
import com.xgaslan.stockservice.repository.ProcessedOrderRepository;
import com.xgaslan.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.Acknowledgment;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumerService {

    private final StockService stockService;
    private final ProcessedOrderRepository processedOrderRepository;

    @KafkaListener(
            id = "orderConsumerClient",
            topics = KafkaConstants.ORDER_TOPIC_NAME,
            groupId = KafkaConstants.GROUP_ID,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessage(OrderEvent event, Acknowledgment ack) {
        try {
            log.info("Processing order: {}", event.getOrder().getId());

//             İdempotent check
            if (!isOrderProcessed(event.getOrder().getId())) {
                updateStock(event.getOrder());
                markOrderAsProcessed(event.getOrder().getId());
                log.info("Stock updated for order: {}", event.getOrder().getId());
            } else {
                log.info("Order already processed, skipping: {}", event.getOrder().getId());
            }

            ack.acknowledge();

        } catch (Exception e) {
            log.error("Stock update failed for order: {}", event.getOrder().getId(), e);
        }
    }

    private boolean isOrderProcessed(String orderId) {
        return processedOrderRepository.existsByOrderId(orderId);
    }

    private void updateStock(Order order) {
        stockService.decreaseStock(order.getName(), order.getQuantity());
    }

    private void markOrderAsProcessed(String orderId) {
        ProcessedOrder processedOrder = ProcessedOrder.builder()
                .orderId(orderId)
                .processedAt(LocalDateTime.now())
                .build();
        processedOrderRepository.save(processedOrder);
    }
}
