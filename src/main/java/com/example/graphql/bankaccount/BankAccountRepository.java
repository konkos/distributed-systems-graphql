package com.example.graphql.bankaccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
}
