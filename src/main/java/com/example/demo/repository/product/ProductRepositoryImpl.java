package com.example.demo.repository.product;

import com.example.demo.repository.product.entity.Product;
import com.example.demo.repository.product.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findAllById(Integer id) {
        QProduct p = QProduct.product;

        return queryFactory
                .selectFrom(p)
                .where(
                        p.user.id.eq(id)
                )
                .fetch();
    }

    @Override
    public Page<Product> findByStatus(Product.ProductStatus status, Pageable pageable) {
        QProduct p = QProduct.product;

        // 상품 페이징
        List<Product> products = queryFactory
                .selectFrom(p)
                .where(p.status.eq(status))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수
        Long total = queryFactory
                .select(p.count())
                .from(p)
                .where(p.status.eq(status))
                .fetchOne();

        return new PageImpl<>(products, pageable, total != null ? total : 0L);
    }
}
