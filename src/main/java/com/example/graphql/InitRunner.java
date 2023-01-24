package com.example.graphql;

import com.example.graphql.bankaccount.BankAccount;
import com.example.graphql.bankaccount.BankAccountRepository;
import com.example.graphql.bankaccount.BankUser;
import com.example.graphql.bankaccount.BankUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class InitRunner implements CommandLineRunner {

    private final BankUserRepository bankUserRepository;
    private final BankAccountRepository bankAccountRepository;

    public InitRunner(BankUserRepository bankUserRepository, BankAccountRepository bankAccountRepository) {
        this.bankUserRepository = bankUserRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public void run(String... args) {

        if (bankUserRepository.count() == 0) addBankUsers();

        if (bankAccountRepository.count() == 0) addBankAccounts();
    }

    @Transactional
    private void addBankAccounts() {
        log.info("BANK USERS");
        for (int i = 1; i < 10; i++) {
            BankUser bankUser = bankUserRepository.findById(i).orElseThrow();

            BankAccount bankAccount = BankAccount.builder().amount(100).build();
            bankAccountRepository.save(bankAccount);

            bankAccount.setUser(bankUser);
            bankUser.setAccount(bankAccount);
            bankUserRepository.save(bankUser);
            bankAccountRepository.save(bankAccount);

            log.info(bankUser.toString());
            log.info(bankAccount.toString());

        }
    }

    @Transactional
    private void addBankUsers() {
        for (int i = 1; i < 10; i++) {
            BankUser bankUser = BankUser.builder().userName("name" + i).build();
            bankUserRepository.save(bankUser);
        }
    }
}
