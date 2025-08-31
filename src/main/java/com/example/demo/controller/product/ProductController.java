package com.example.demo.controller.product;

import com.example.demo.controller.product.dto.ProductCreateRequestDto;
import com.example.demo.controller.product.dto.ProductResponseDto;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.user.entity.User;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.repository.product.entity.QProduct.product;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserRepository userRepository;

//    @GetMapping("/{id}")
//    public ResponseEntity<List<ProductResponseDto>> products(@AuthenticationPrincipal User user) {
//        if (user == null) {
//            return ResponseEntity.status(401).build();
//        }
//        List<ProductResponseDto> products = productService.findAllbyId(user.getId());
//        return ResponseEntity.ok(products);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductResponseDto>> products(@RequestHeader("X-USER-ID") Integer userId) {
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        List<ProductResponseDto> products = productService.findAllbyId(userId);
        return ResponseEntity.ok(products);
    }

//    @PostMapping("")
//    public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductCreateRequestDto request, @AuthenticationPrincipal User user) {
//        ProductResponseDto product = productService.save(request, user);
//        return ResponseEntity.ok(product);
//    }

    @PostMapping("")
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductCreateRequestDto request, @RequestHeader("X-USER-ID") Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("not exist userId"));
        ProductResponseDto product = productService.save(request, user);
        return ResponseEntity.ok(product);
    }
}
