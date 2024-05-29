package com.unibank.demo.security.requests;

import lombok.Data;

@Data
public class RefreshRequest {
    Long userId;
    String refreshToken;
}
