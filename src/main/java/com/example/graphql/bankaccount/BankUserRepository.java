package com.example.graphql.bankaccount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankUserRepository extends JpaRepository<BankUser, Integer> {
    Optional<BankUser> findByUserName(String userName);
}
