package com.unibank.demo.exception;

import com.unibank.demo.enums.ErrorCodeEnum;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int code;
    private final String message;

    public CustomException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getMessage();
    }
}
