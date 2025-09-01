package com.example.demo.service;

import com.example.demo.controller.product.dto.ImageSimpleDto;
import com.example.demo.controller.product.dto.ProductCreateRequestDto;
import com.example.demo.controller.product.dto.ProductResponseDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.repository.image.ImageRepository;
import com.example.demo.repository.image.entity.Image;
import com.example.demo.repository.product.BaseRepository;
import com.example.demo.repository.product.ProductImageRepository;
import com.example.demo.repository.product.ProductRepository;
import com.example.demo.repository.product.entity.Base;
import com.example.demo.repository.product.entity.Product;
import com.example.demo.repository.product.entity.ProductImage;
import com.example.demo.repository.product.entity.ReviewHistory;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.user.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BaseRepository baseRepository;
    private final ImageRepository imageRepository;
    private final ProductImageRepository productImageRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;

    @PostConstruct
    public void initSaleProducts() {
        User user = userRepository.findAll().stream().findFirst()
                .orElseGet(() -> userRepository.save(User.create("testuser", "password", "테스트유저")));
        Base base = baseRepository.findAll().stream().findFirst()
                .orElseGet(() -> baseRepository.save(Base.create("테스트베이스", 1000.0)));

        for (int i = 1; i <= 3; i++) {
            Product product = Product.create("SALE 상품 " + i, user, base);
            // 상태를 SALE로 변경
            product = new Product(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    product.isVisible(),
                    Product.ProductStatus.ENROLL,
                    product.getUser(),
                    product.getBase(),
                    product.getProductImages()
            );
            productRepository.save(product);
        }
    }

    @Transactional
    public List<ProductResponseDto> findAllbyId(Integer id) {
        List<Product> products = productRepository.findAllById(id);
        return products.stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    @Transactional
    public List<ProductResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Product> products = productRepository.findByStatus(Product.ProductStatus.ENROLL, pageable);
        return products.stream()
                .map(ProductResponseDto::simple)
                .toList();
    }

    @Transactional
    public ProductResponseDto save(ProductCreateRequestDto request, User user) {
        // 1. 베이스 제품 ID 검증
        Integer baseId = request.getBaseId();
        Base base = baseRepository.findById(baseId)
                .orElseThrow(() -> new CustomException(ExceptionType.BASE_NOT_FOUND, baseId));// RuntimeException vs IllegalArgumentException

        // 사진 확인 -> X, 최초 등록이니까 사진도 생성해야함
//        List<Image> Images = request.getImageIds().stream()
//                .map((id) -> imageRepository.findById(id)
//                        .orElseThrow(() -> new RuntimeException("not exist ImageId"))
////                ).toList();

        // 2. Product 엔티티 생성
        Product product = Product.create(
                request.getName(),
                user,
                base
        );

        // 3. 이미지 개별 저장 -> ProductImage 저장
        for (String image : request.getImages()) {
            Image createdImg = imageService.save(image);
            ProductImage productImage = ProductImage.create(product, createdImg);

            product.addProductImage(productImage); // 연관관계 편의 메서드@
        }

        // 4. Product 저장으로 한 번에 저장
        Product created = productRepository.save(product);


//        // Product 저장 - cascade
//            // 저장 먼저 하고 image 할당?
//        Product product = Product.create(
//                request.getName(),
//                base
//        );
//        Product created = productRepository.save(product);
//
//        // ProductImage 할당
//        List<ProductImage> productImages = Images.stream()
//                .map(image -> ProductImage.create(product, image))
//                .toList();
//        productImageRepository.saveAll(productImages);
//
//        // Product에도 이미지 할당해주기(양방향)
//        created.update(productImages);

        return ProductResponseDto.from(created);
    }

    @Transactional
    public ProductResponseDto changeStauts(Integer productId, String status) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionType.PRODUCT_NOT_FOUND, productId));

        // 상태 확인
        Product.ProductStatus newStatus = Product.ProductStatus.valueOf(status);
        Product.ProductStatus curStatus = product.getStatus();

        if (newStatus == Product.ProductStatus.APPROVE) {
            if (!(curStatus == Product.ProductStatus.ENROLL ||
                    curStatus == Product.ProductStatus.REJECT ||
                    curStatus == Product.ProductStatus.BAN)) {
                throw new CustomException(ExceptionType.INVALID_PRODUCT_STATUS, status);
            }
        } else if (newStatus == Product.ProductStatus.REJECT) {
            if (curStatus != Product.ProductStatus.ENROLL) {
                throw new CustomException(ExceptionType.INVALID_PRODUCT_STATUS, status);
            }
        } else if (newStatus == Product.ProductStatus.BAN) {
            if (curStatus != Product.ProductStatus.APPROVE) {
                throw new CustomException(ExceptionType.INVALID_PRODUCT_STATUS, status);
            }
        } else {
            throw new CustomException(ExceptionType.INVALID_PRODUCT_STATUS, status);
        }

        // 상태 변경
        product = new Product(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.isVisible(),
                newStatus,
                product.getUser(),
                product.getBase(),
                product.getProductImages()
        );
        productRepository.save(product);

        // 히스토리 생성
        for (ProductImage pi : product.getProductImages()) {
            pi.getReviewHistories().add(
                    ReviewHistory.create(pi)
            );
        }

        return ProductResponseDto.from(product);
    }
}
