package com.xgaslan.stockservice.service;

import com.xgaslan.stockservice.entity.Stock;
import com.xgaslan.stockservice.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    public void decreaseStock(String productName, Integer quantity) {
        Stock stock = stockRepository.findByProductName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));

        if (stock.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + productName +
                    ". Available: " + stock.getQuantity() + ", Requested: " + quantity);
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stock.setUpdatedAt(LocalDateTime.now());

        stockRepository.save(stock);

        log.info("Stock decreased for product: {}. New quantity: {}",
                productName, stock.getQuantity());
    }

    public Stock getStock(String productName) {
        return stockRepository.findByProductName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));
    }

    public void increaseStock(String productName, Integer quantity) {
        Stock stock = stockRepository.findByProductName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));

        stock.setQuantity(stock.getQuantity() + quantity);
        stock.setUpdatedAt(LocalDateTime.now());

        stockRepository.save(stock);

        log.info("Stock increased for product: {}. New quantity: {}",
                productName, stock.getQuantity());
    }
}