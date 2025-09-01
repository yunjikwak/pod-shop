package com.example.demo.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    USER_NOT_FOUND(404, "유저가 데이터베이스 내 존재하지 않습니다. 유저 id : "),
    PRODUCT_NOT_FOUND(404, "상품이 존재하지 않습니다. 상품 id : "),
    BASE_NOT_FOUND(404, "베이스가 존재하지 않습니다. 베이스 id : "),
    INVALID_PRODUCT_STATUS(400, "잘못된 상품 상태입니다. status : "),
    DUPLICATED_LOGIN_ID(400, "중복된 로그인 아이디입니다. 아이디 : "),
    INTERNAL_SERVER_ERROR(500, "내부적 에러 발생");

    private final int status;
    private final String message;

    ExceptionType(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
