package com.example.demo.repository.product;

import com.example.demo.repository.product.entity.Base;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository extends JpaRepository<Base, Integer> {
}
