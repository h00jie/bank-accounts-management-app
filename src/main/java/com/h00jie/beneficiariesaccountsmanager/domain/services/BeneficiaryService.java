package com.h00jie.beneficiariesaccountsmanager.domain.services;

import com.h00jie.beneficiariesaccountsmanager.domain.models.*;
import com.h00jie.beneficiariesaccountsmanager.domain.repositories.BeneficiaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BeneficiaryService {

    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryService.class);


    private final BeneficiaryRepository beneficiaryRepository;

    public BeneficiaryService(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    public Beneficiary getBeneficiaryById(String beneficiaryId) {
        logger.info("Αναζήτηση δικαιούχου με ID: {}", beneficiaryId);
        return beneficiaryRepository.findById(beneficiaryId);
    }

    public List<Account> getAccountsByBeneficiaryId(String beneficiaryId) {
        logger.info("Αναζήτηση λογαριασμών δικαιούχου με ID: {}", beneficiaryId);
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId);
        if (beneficiary != null) {
            return beneficiary.getAccounts();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Transaction> getTransactionsByBeneficiaryId(String beneficiaryId) {
        logger.info("Αναζήτηση συναλλαγών δικαιούχου με ID: {}", beneficiaryId);
        List<Transaction> allTransactions = new ArrayList<>();
        List<Account> accounts = getAccountsByBeneficiaryId(beneficiaryId);

//        return getAccountsByBeneficiaryId(beneficiaryId).stream()
//                .flatMap(account -> account.getTransactions().stream())
//                .collect(Collectors.toList());

        for (Account account : accounts) {
            allTransactions.addAll(account.getTransactions());
        }
        return allTransactions;
    }

    public Money getTotalBalanceByBeneficiaryId(String beneficiaryId) {
        logger.info("Υπόλοιπο λογαριασμού του δικαιούχου με ID: {}", beneficiaryId);
        List<Account> accounts = getAccountsByBeneficiaryId(beneficiaryId);
        Money totalBalance = new Money(BigDecimal.ZERO);
        for (Account account : accounts) {
            totalBalance = totalBalance.add(account.getBalance());
        }
        return totalBalance;
    }


    public Transaction getLargestWithdrawalLastMonthByBeneficiaryId(String beneficiaryId) {
        logger.info("Αναζήτηση μεγαλύτερης ανάληψης του τελευταίου μήνα για δικαιούχο με ID: {}", beneficiaryId);

        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        List<Transaction> withdrawals = getWithdrawalsByBeneficiaryIdWithDatePredicate(beneficiaryId, date -> !date.isBefore(oneMonthAgo));

        Transaction largestWithdrawal = withdrawals.stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);

        if (largestWithdrawal != null) {
            logger.info("Βρέθηκε μεγαλύτερη ανάληψη: Transaction ID: {}, Ποσό: {}, Ημερομηνία: {}",
                    largestWithdrawal.getTransactionId(),
                    largestWithdrawal.getAmount().getAmount(),
                    largestWithdrawal.getTransactionDate());
        } else {
            logger.info("Δεν βρέθηκε καμία ανάληψη για τον δικαιούχο με ID: {} τον τελευταίο μήνα.", beneficiaryId);
        }

        return largestWithdrawal;
    }

    private List<Transaction> getWithdrawalsByBeneficiaryIdWithDatePredicate(String beneficiaryId, Predicate<LocalDate> datePredicate) {
        List<Transaction> transactions = getTransactionsByBeneficiaryId(beneficiaryId);
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.WITHDRAWAL)
                .filter(t -> datePredicate.test(t.getTransactionDate()))
                .collect(Collectors.toList());
    }

    private boolean beneficiaryHasWithdrawalWithDatePredicate(Beneficiary beneficiary, Predicate<LocalDate> datePredicate) {
        return getTransactionsByBeneficiaryId(beneficiary.getBeneficiaryId()).stream()
                .filter(t -> t.getType() == TransactionType.WITHDRAWAL)
                .anyMatch(t -> datePredicate.test(t.getTransactionDate()));
    }

    public List<Beneficiary> getBeneficiariesWithWithdrawalsBeforeOneMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        List<Beneficiary> allBeneficiaries = beneficiaryRepository.findAll();

        return allBeneficiaries.stream()
                .filter(beneficiary -> beneficiaryHasWithdrawalWithDatePredicate(beneficiary, date -> date.isBefore(oneMonthAgo)))
                .collect(Collectors.toList());
    }

}
