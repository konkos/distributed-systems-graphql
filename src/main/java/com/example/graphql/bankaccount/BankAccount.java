package com.example.graphql.bankaccount;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double amount;
    @OneToOne(mappedBy = "account")
    private BankUser user;
}
