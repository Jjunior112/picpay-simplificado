package com.picpay_simplificado.domain.user;

import com.picpay_simplificado.Dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDto user)
    {
        this.firstname = user.firstName();
        this.lastName = user.lastName();
        this.email = user.email();
        this.document = user.document();
        this.password = user.password();
        this.balance = user.balance();
        this.userType = user.userType();

    }

}
