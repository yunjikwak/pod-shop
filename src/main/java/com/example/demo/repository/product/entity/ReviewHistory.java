package com.example.demo.repository.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewHistory {

    public enum ReviewHistoryStatus {
        PENDING,
        ENROLL,
        APPROVE,
        REJECT,
        BAN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_image_id")
    private ProductImage productImage;

    private LocalDateTime createdAt;
    private ReviewHistoryStatus status;

    public static ReviewHistory create(ProductImage productImage) {
        return new ReviewHistory(
                null,
                productImage,
                LocalDateTime.now(),
                ReviewHistoryStatus.PENDING
        );
    }

}
