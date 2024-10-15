package com.h00jie.beneficiariesaccountsmanager.domain.models;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Account {

    @Schema(description = "Το μοναδικό ID του λογαριασμού", example = "15")
    private final String accountId;

    @Schema(description = "Το ID του δικαιούχου", example = "1")
    private final String beneficiaryId;

    @Schema(description = "Το υπόλοιπο του λογαριασμού", example = "500.00")
    private Money balance;

    @Schema(description = "Οι συναλλαγές του λογαριασμού")
    @ArraySchema(schema = @Schema(implementation = Transaction.class))
    private final List<Transaction> transactions;

    public Account(String accountId, String beneficiaryId) {
        this.accountId = accountId;
        this.beneficiaryId = beneficiaryId;
        this.balance = new Money(BigDecimal.ZERO);
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateBalance(transaction);
    }

    private void updateBalance(Transaction transaction) {
        if (transaction.getType() == TransactionType.DEPOSIT) {
            balance = balance.add(transaction.getAmount());
        } else if (transaction.getType() == TransactionType.WITHDRAWAL) {
            balance = balance.subtract(transaction.getAmount());
        }
    }

}
