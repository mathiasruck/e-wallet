package com.mathiasruck.ewallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathiasruck.ewallet.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal value;

    private BigDecimal finalBalance;

    private OffsetDateTime executionDate;

    private TransactionType transactionType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

}
