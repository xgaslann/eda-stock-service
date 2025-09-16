package com.xgaslan.stockservice.repository;

import com.xgaslan.stockservice.entity.ProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedOrderRepository extends JpaRepository<ProcessedOrder, Long> {
    boolean existsByOrderId(String orderId);
}
