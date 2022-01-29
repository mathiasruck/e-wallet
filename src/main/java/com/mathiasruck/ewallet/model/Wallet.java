package com.mathiasruck.ewallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Where(clause = "DELETED IS NULL")
@SQLDelete(sql = "UPDATE WALLET SET DELETED = id WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100, message = "owner_full_name_bigger_than_allowed")
    @NotNull(message = "name_cannot_be_null")
    private String ownerFullName;

    @Min(value = 0, message = "balance_cannot_be_smaller_than_zero")
    @NotNull(message = "balance_cannot_be_null")
    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<TransactionHistory> transactionHistory;

    @JsonIgnore
    @Column(name = "DELETED", insertable = false, updatable = false)
    private Long deleted;

}