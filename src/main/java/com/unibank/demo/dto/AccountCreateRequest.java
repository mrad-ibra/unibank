package com.unibank.demo.dto;

import java.math.BigDecimal;

public record AccountCreateRequest(
        Long accountNumber,
        BigDecimal balance,
        String currencyType
) {
}
