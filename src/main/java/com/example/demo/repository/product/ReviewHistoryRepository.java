package com.example.demo.repository.product;

import com.example.demo.repository.product.entity.ReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewHistoryRepository extends JpaRepository<ReviewHistory, Integer> {
}
