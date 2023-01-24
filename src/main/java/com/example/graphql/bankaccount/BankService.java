package com.example.graphql.bankaccount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class BankService {

    private final BankAccountRepository bankAccountRepository;
    private final BankUserRepository bankUserRepository;

    public BankService(BankAccountRepository bankAccountRepository, BankUserRepository bankUserRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankUserRepository = bankUserRepository;
    }

    public BankAccount getAccount(String userName) {
        BankUser bankUser = bankUserRepository.findByUserName(userName).orElseThrow();
        return bankUser.getAccount();
    }

    public BankUser getBankUser(String userName) {
        return bankUserRepository.findByUserName(userName).orElseThrow();

    }

    @Transactional(readOnly = true)
    public List<BankUser> getAllBankUsers() {
        return bankUserRepository.findAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BankUser createUser(String userName) {
//        int i = Integer.parseInt(String.valueOf(bankUserRepository.count())) + 1;
        BankUser createdUser = BankUser.builder().userName(userName).build();
        BankUser userToBeSaved = bankUserRepository.save(createdUser);
        log.info(userToBeSaved.toString());
        return userToBeSaved;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BankAccount createAccount(String userName) {
        log.error("createAccount");

        BankUser bankUser = bankUserRepository.findByUserName(userName).orElseThrow();
        log.info("BANKUSER " + bankUser);

//        int i = Integer.parseInt(String.valueOf(bankAccountRepository.count())) + 1;
        BankAccount build = BankAccount.builder().user(bankUser).amount(0).build();
        log.info("build  " + build.toString());

        BankAccount saved = bankAccountRepository.save(build);
        log.info("saved  " + saved);


        bankUser.setAccount(build);
        bankUserRepository.save(bankUser);
        log.info(build.toString());

        return saved;
    }

    @Transactional
    public BankAccount addAmount(String userName, Float amount) {
        BankUser bankUser = bankUserRepository.findByUserName(userName).orElseThrow();

        BankAccount account = bankUser.getAccount();
        double amount1 = account.getAmount();
        account.setAmount(amount1 + amount);

        BankUser save = bankUserRepository.save(bankUser);
        return save.getAccount();
    }

    @Transactional
    public Boolean transferAmount(String userNameSender, String userNameReceiver, Float amount) {
        if (amount <= 0) throw new RuntimeException("Cannot Send Negative Amount");

        BankUser sender = bankUserRepository.findByUserName(userNameSender).orElseThrow();
        BankUser receiver = bankUserRepository.findByUserName(userNameReceiver).orElseThrow();

        BankAccount senderAccount = sender.getAccount();
        senderAccount.setAmount(senderAccount.getAmount() - amount);

        BankAccount receiverAccount = receiver.getAccount();
        receiverAccount.setAmount(receiverAccount.getAmount() + amount);

        return true;
    }
}
