package com.example.demo.repository.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 베이스가 product 정보를 가질 필요는 X => 단방향

    public static Base create(String name, double price) {
        return new Base(
                null,
                name,
                null,
                price,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
