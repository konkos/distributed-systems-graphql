package com.example.graphql.bankaccount;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BankUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String userName;

    @OneToOne
    private BankAccount account;


}
