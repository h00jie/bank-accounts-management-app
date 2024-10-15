package com.h00jie.beneficiariesaccountsmanager.domain.models;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(description = "Μοντέλο Συναλλαγής")
public class Transaction {

    // Getters
    @Schema(description = "Το μοναδικό ID της συναλλαγής", example = "15")
    private final String transactionId;

    @Schema(description = "Το ID του λογαριασμού", example = "16")
    private final String accountId;

    @Schema(description = "Το ποσό της συναλλαγής", example = "457.2")
    private final Money amount;

    @Schema(description = "Ο τύπος της συναλλαγής: DEPOSIT (κατάθεση), WITHDRAWAL (ανάληψη)", example = "WITHDRAWAL")
    private final TransactionType type;

    @Schema(description = "Η ημερομηνία της συναλλαγής", example = "2023-10-01")
    private final LocalDate transactionDate;

    public Transaction(String transactionId, String accountId, Money amount, TransactionType type, LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.transactionDate = transactionDate;
    }

}
