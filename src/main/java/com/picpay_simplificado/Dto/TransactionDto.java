package com.picpay_simplificado.Dto;

import java.math.BigDecimal;

public record TransactionDto(BigDecimal value,Long senderId,Long receiverId) {
}
