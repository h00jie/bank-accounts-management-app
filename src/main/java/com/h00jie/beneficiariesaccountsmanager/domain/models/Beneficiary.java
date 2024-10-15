package com.h00jie.beneficiariesaccountsmanager.domain.models;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Μοντέλο Δικαιούχου")
public class Beneficiary {

    // Getters
    @Schema(description = "Το μοναδικό ID του δικαιούχου", example = "1")
    private final String beneficiaryId;

    @Schema(description = "Όνομα", example = "John")
    private String firstName;

    @Schema(description = "Επώνυμο", example = "Wick")
    private String lastName;

    @Schema(description = "Οι λογαριασμοί του δικαιούχου")
    @ArraySchema(schema = @Schema(implementation = Account.class))
    private List<Account> accounts;

    public Beneficiary(String beneficiaryId, String firstName, String lastName) {
        this.beneficiaryId = beneficiaryId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

}
