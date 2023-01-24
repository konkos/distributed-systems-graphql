package com.example.graphql.bankaccount;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @QueryMapping
    public BankAccount getAccount(@Argument String userName) {
        return bankService.getAccount(userName);
    }

    @SchemaMapping
    public BankUser getBankUser(String userName) {
        return bankService.getBankUser(userName);
    }

    @SchemaMapping(typeName = "BankAccount")
    public BankAccount getBankAccount(BankUser bankUser) {
        return bankService.getAccount(bankUser.getUserName());
    }

    @QueryMapping
    public List<BankUser> getUsers() {
        return bankService.getAllBankUsers();
    }

    @QueryMapping
    public BankUser getUser(@Argument String userName) {
        return bankService.getBankUser(userName);
    }

    @MutationMapping
    public BankUser createUser(@Argument String userName) {
        return bankService.createUser(userName);
    }

    @MutationMapping
    public BankAccount createAccount(@Argument String userName) {
        return bankService.createAccount(userName);
    }

    @MutationMapping
    public BankAccount addAmount(@Argument String userName, @Argument Float amount) {
        return bankService.addAmount(userName, amount);
    }

    @MutationMapping
    public Boolean transferAmount(@Argument String userNameSender, @Argument String userNameReceiver, @Argument Float amount) {
        return bankService.transferAmount(userNameSender, userNameReceiver, amount);
    }

}
