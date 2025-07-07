package com.picpay_simplificado.domain.transaction;

import com.picpay_simplificado.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name="transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name="sender-id")
    private User sender;

    @ManyToOne
    @JoinColumn(name="receiver-id")
    private User receiver;

    private LocalDateTime timestamp;


}
