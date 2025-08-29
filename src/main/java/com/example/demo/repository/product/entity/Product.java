package com.example.demo.repository.product.entity;

import com.example.demo.repository.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    public enum ProductStatus {
        PENDING,
        SALE,
        STOP
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isVisible;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="base_id")
    private Base base;

    // 양방향
        // 부모 저장 시 자식도 저장되게 -> cascade
        // orphanRemoval -> 고아 객체, 즉 product가 없는 image 제거
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    public static Product create(String name, Base base) {
        return new Product(
                null,
                name,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                false,
                ProductStatus.PENDING,
                null, // null?
                base,
                new ArrayList<>()
        );
    }

//    // 이미지 리스트 갱신하기
//    public void update(List<ProductImage> productImages) {
//        this.productImages = productImages;
//    }

    // 연관관계 편의 메서드
        // 직접 추가하게 하기!
    public void addProductImage(ProductImage productImage) {
        this.productImages.add(productImage); // 내 리스트에 추가
        productImage.setProduct(this); // 자식에게도 부모 알림
    }

}
