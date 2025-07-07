package com.picpay_simplificado.Dto;

import com.picpay_simplificado.domain.user.UserType;

import java.math.BigDecimal;

public record UserDto(String firstName, String lastName, String document, BigDecimal balance, String email, String password,
                      UserType userType) {

}
