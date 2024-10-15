package com.h00jie.beneficiariesaccountsmanager.domain.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Τύπος Συναλλαγής")
public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL
}