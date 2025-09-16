package com.xgaslan.stockservice.repository;

import com.xgaslan.stockservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductName(String productName);
}
