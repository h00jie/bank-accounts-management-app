package com.h00jie.beneficiariesaccountsmanager.infrastructure;

import com.h00jie.beneficiariesaccountsmanager.domain.models.Account;
import com.h00jie.beneficiariesaccountsmanager.domain.models.Beneficiary;
import com.h00jie.beneficiariesaccountsmanager.domain.models.Transaction;
import com.h00jie.beneficiariesaccountsmanager.domain.repositories.BeneficiaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final BeneficiaryRepository beneficiaryRepository;

    public DataInitializer(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @PostConstruct
    public void init() {
        logger.info("Έναρξη εισαγωγής δεδομένων απο τα αρχεία csv beneficiaries.csv, accounts.csv, transactions.csv");

        BeneficiaryCsvLoader beneficiaryCsvLoader = new BeneficiaryCsvLoader();
        List<Beneficiary> beneficiaries = beneficiaryCsvLoader.loadBeneficiaries("beneficiaries.csv");
        logger.info("Φορτώθηκαν {} δικαιούχοι από το beneficiaries.csv", beneficiaries.size());

        AccountsCsvLoader accountCsvLoader = new AccountsCsvLoader();
        List<Account> accounts = accountCsvLoader.loadAccounts("accounts.csv");
        logger.info("Φορτώθηκαν {} λογαριασμοί από το accounts.csv", accounts.size());

        TransactionCsvLoader transactionCsvLoader = new TransactionCsvLoader();
        List<Transaction> transactions = transactionCsvLoader.loadTransactions("transactions.csv");
        logger.info("Φορτώθηκαν {} συναλλαγές από το transactions.csv", transactions.size());

        Map<String, Beneficiary> beneficiaryMap = new HashMap<>();
        for (Beneficiary beneficiary : beneficiaries) {
            logger.debug("Επεξεργασία δικαιούχου: {}", beneficiary.getBeneficiaryId());
            beneficiaryMap.put(beneficiary.getBeneficiaryId(), beneficiary);
        }

        for (Account account : accounts) {
            logger.debug("Επεξεργασία λογαριασμού: {}", account.getAccountId());
            Beneficiary beneficiary = beneficiaryMap.get(account.getBeneficiaryId());
            if (beneficiary != null) {
                logger.debug("Προσθήκη λογαριασμού {} στον δικαιούχο {}", account.getAccountId(), beneficiary.getBeneficiaryId());
                beneficiary.addAccount(account);
            } else {
                logger.warn("Δεν βρέθηκε δικαιούχος για τον λογαριασμό {}. Δημιουργία άγνωστου δικαιούχου.", account.getAccountId());
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
            logger.debug("Επεξεργασία συναλλαγής: {}", transaction.getTransactionId());
            Account account = accountMap.get(transaction.getAccountId());
            if (account != null) {
                logger.debug("Προσθήκη συναλλαγής {} στον λογαριασμό {}", transaction.getTransactionId(), account.getAccountId());
                account.addTransaction(transaction);
            } else {
                logger.error("Δεν βρέθηκε λογαριασμός για τη συναλλαγή: {}", transaction.getTransactionId());
            }
        }

        for (Beneficiary beneficiary : beneficiaryMap.values()) {
            logger.info("Αποθήκευση δικαιούχου: {}", beneficiary.getBeneficiaryId());
            beneficiaryRepository.save(beneficiary);
        }

        logger.info("Η αρχικοποίηση δεδομένων ολοκληρώθηκε.");
    }
}
