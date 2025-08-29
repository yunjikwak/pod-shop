package com.example.demo.repository.product.entity;

import com.example.demo.repository.image.entity.Image;
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
public class ProductImage {
    public enum ProductImageStatus {
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
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Image_id")
    private Image image;

    @Enumerated(EnumType.STRING)
    private ProductImageStatus status;

    private LocalDateTime createdAt;

    public static ProductImage create(Product product, Image image) {
        return new ProductImage(
                null,
                product,
                image,
                ProductImageStatus.PENDING,
                LocalDateTime.now()
        );
    }

    // 같은 패키지 안 접근만 허용 -> protected !
    protected void setProduct(Product product) {
        this.product = product;
    }

}
