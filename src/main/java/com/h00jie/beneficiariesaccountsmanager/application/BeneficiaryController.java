package com.h00jie.beneficiariesaccountsmanager.application;


import com.h00jie.beneficiariesaccountsmanager.domain.models.Account;
import com.h00jie.beneficiariesaccountsmanager.domain.models.Beneficiary;
import com.h00jie.beneficiariesaccountsmanager.domain.models.Money;
import com.h00jie.beneficiariesaccountsmanager.domain.models.Transaction;
import com.h00jie.beneficiariesaccountsmanager.domain.services.BeneficiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beneficiaries")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    public BeneficiaryController(BeneficiaryService beneficiaryService) {
        this.beneficiaryService = beneficiaryService;
    }


    @Operation(
            summary = "Ανάκτηση στοιχείων δικαιούχου",
            description = "Επιστρέφλει τα στοιχεία ενός δικαιούχου με βάση το ID του.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Επιτυχής ανάκτηση δικαιούχου"),
                    @ApiResponse(responseCode = "404", description = "Ο δικαιούχος δεν βρέθηκε")
            }
    )
    @GetMapping(value = "/{beneficiaryId}", produces = MediaType.APPLICATION_JSON_VALUE)    public ResponseEntity<Beneficiary> getBeneficiary(@PathVariable String beneficiaryId) {
        Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(beneficiaryId);
        if (beneficiary != null) {
            return ResponseEntity.ok(beneficiary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            summary = "Ανάκτηση λογαριασμών δικαιούχου",
            description = "Επιστρέφει τη λίστα των λογαριασμών για έναν συγκεκριμένο δικαιούχο.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Επιτυχής ανάκτηση λογαριασμών"),
                    @ApiResponse(responseCode = "404", description = "Ο δικαιούχος δεν βρέθηκε")
            }
    )
    @GetMapping("/{beneficiaryId}/accounts")
    public ResponseEntity<List<Account>> getAccounts(@PathVariable String beneficiaryId) {
        List<Account> accounts = beneficiaryService.getAccountsByBeneficiaryId(beneficiaryId);
        return ResponseEntity.ok(accounts);
    }

    @Operation(
            summary = "Ανάκτηση συναλλαγών δικαιούχου",
            description = "Επιστρέφει τη λίστα όλων των συναλλαγών για έναν συγκεκριμένο δικαιούχο.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Επιτυχής ανάκτηση συναλλαγών"),
                    @ApiResponse(responseCode = "404", description = "Ο δικαιούχος δεν βρέθηκε")
            }
    )
    @GetMapping("/{beneficiaryId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String beneficiaryId) {
        List<Transaction> transactions = beneficiaryService.getTransactionsByBeneficiaryId(beneficiaryId);
        return ResponseEntity.ok(transactions);
    }


    @Operation(
            summary = "Ανάκτηση συνολικό υπόλοιπο δικαιούχου",
            description = "Επιστρέφει το συνολικό υπόλοιπο όλων των λογαριασμών ενός συγκεκριμένου δικαιούχου.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Επιτυχής ανάκτηση υπολοίπου"),
                    @ApiResponse(responseCode = "404", description = "Ο δικαιούχος δεν βρέθηκε")
            }
    )
    @GetMapping("/{beneficiaryId}/balance")
    public ResponseEntity<Money> getTotalBalance(@PathVariable String beneficiaryId) {
        Money totalBalance = beneficiaryService.getTotalBalanceByBeneficiaryId(beneficiaryId);
        return ResponseEntity.ok(totalBalance);
    }


    @Operation(
            summary = "Ανάκτηση της μεγαλύτερης ανάληψης του τελευταίου μήνα",
            description = "Επιστρέφει τη μεγαλύτερη ανάληψη που έχει πραγματοποιήσει ο δικαιούχος τον τελευταίο μήνα.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Επιτυχής ανάκτηση της μεγαλύτερης ανάληψης"),
                    @ApiResponse(responseCode = "404", description = "Δεν βρέθηκε ανάληψη για τον δικαιούχο τον τελευταίο μήνα")
            }
    )
    @GetMapping("/{beneficiaryId}/largest-withdrawal")
    public ResponseEntity<Transaction> getLargestWithdrawal(@PathVariable String beneficiaryId) {
        Transaction transaction = beneficiaryService.getLargestWithdrawalLastMonthByBeneficiaryId(beneficiaryId);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    @Operation(
//            summary = "Ανάκτηση δικαιούχων με πρόσφατες αναλήψεις",
//            description = "Επιστρέφει τη λίστα των δικαιούχων του τελευταίου μήνα που έχουν αναλήψεις.",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Επιτυχής ανάκτηση συναλλαγών"),
//                    @ApiResponse(responseCode = "404", description = "Ο δικαιούχος δεν βρέθηκε")
//            }
//    )
//    @GetMapping("/beneficiaries-recent-withdrawals")
//    public ResponseEntity<List<Beneficiary>> getRecentWithdrawals(
//    ) {
//
//        List<Beneficiary> beneficiariesWithWithdrawalsBeforeOneMonth = beneficiaryService.getBeneficiariesWithWithdrawalsBeforeOneMonth();
//        if (beneficiariesWithWithdrawalsBeforeOneMonth != null && !beneficiariesWithWithdrawalsBeforeOneMonth.isEmpty()) {
//            return ResponseEntity.ok(beneficiariesWithWithdrawalsBeforeOneMonth);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
