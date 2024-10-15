package com.h00jie.beneficiariesaccountsmanager.infrastructure;

import com.h00jie.beneficiariesaccountsmanager.domain.models.Account;
import com.h00jie.beneficiariesaccountsmanager.domain.models.Beneficiary;
import com.h00jie.beneficiariesaccountsmanager.domain.models.Transaction;
import com.h00jie.beneficiariesaccountsmanager.domain.repositories.BeneficiaryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer {

    private final BeneficiaryRepository beneficiaryRepository;

    public DataInitializer(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @PostConstruct
    public void init() {
        BeneficiaryCsvLoader beneficiaryCsvLoader = new BeneficiaryCsvLoader();
        List<Beneficiary> beneficiaries = beneficiaryCsvLoader.loadBeneficiaries("beneficiaries.csv");

        AccountsCsvLoader accountCsvLoader = new AccountsCsvLoader();
        List<Account> accounts = accountCsvLoader.loadAccounts("accounts.csv");

        TransactionCsvLoader transactionCsvLoader = new TransactionCsvLoader();
        List<Transaction> transactions = transactionCsvLoader.loadTransactions("transactions.csv");

        Map<String, Beneficiary> beneficiaryMap = new HashMap<>();
        for (Beneficiary beneficiary : beneficiaries) {
            beneficiaryMap.put(beneficiary.getBeneficiaryId(), beneficiary);
        }

        for (Account account : accounts) {
            Beneficiary beneficiary = beneficiaryMap.get(account.getBeneficiaryId());
            if (beneficiary != null) {
                beneficiary.addAccount(account);
            } else {
                Beneficiary newBeneficiary = new Beneficiary(
                        account.getBeneficiaryId(),
                        "Unknown",
                        "Unknown"
                );
                newBeneficiary.addAccount(account);
                beneficiaryMap.put(newBeneficiary.getBeneficiaryId(), newBeneficiary);
            }
        }

        Map<String, Account> accountMap = new HashMap<>();
        for (Beneficiary beneficiary : beneficiaryMap.values()) {
            for (Account account : beneficiary.getAccounts()) {
                accountMap.put(account.getAccountId(), account);
            }
        }

        for (Transaction transaction : transactions) {
            Account account = accountMap.get(transaction.getAccountId());
            if (account != null) {
                account.addTransaction(transaction);
            } else {
                System.out.println("Account not found for transaction: " + transaction.getTransactionId());
            }
        }

        for (Beneficiary beneficiary : beneficiaryMap.values()) {
            beneficiaryRepository.save(beneficiary);
        }
    }
}
