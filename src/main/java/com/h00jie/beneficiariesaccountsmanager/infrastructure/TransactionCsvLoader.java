package com.h00jie.beneficiariesaccountsmanager.infrastructure;

import com.h00jie.beneficiariesaccountsmanager.domain.models.Money;
import com.h00jie.beneficiariesaccountsmanager.domain.models.Transaction;
import com.h00jie.beneficiariesaccountsmanager.domain.models.TransactionType;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionCsvLoader {

    private static final Logger logger = LoggerFactory.getLogger(TransactionCsvLoader.class);

    public List<Transaction> loadTransactions(String fileName) {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        logger.info("Φόρτωση συναλλαγών από το αρχείο: {}", fileName);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                logger.error("Το αρχείο δεν βρέθηκε: {}", fileName);
                return transactions;
            }
            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

                String[] line;
                boolean isFirstLine = true;
                while ((line = reader.readNext()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        logger.debug("Παράκαμψη του header");
                        continue;
                    }

                    String transactionId = line[0];
                    String accountId = line[1];
                    BigDecimal amountValue = new BigDecimal(line[2]);
                    Money amount = new Money(amountValue);
                    TransactionType type = TransactionType.valueOf(line[3].toUpperCase());
                    LocalDate date = LocalDate.parse(line[4], formatter);

                    logger.debug("Επεξεργασία συναλλαγής: {} για τον λογαριασμό: {}", transactionId, accountId);

                    Transaction transaction = new Transaction(
                            transactionId,
                            accountId,
                            amount,
                            type,
                            date
                    );

                    transactions.add(transaction);
                }

                logger.info("Επιτυχής φόρτωση {} συναλλαγών από το αρχείο: {}", transactions.size(), fileName);
            } catch (CsvValidationException e) {
                logger.error("Σφάλμα CSV κατά την ανάγνωση του αρχείου: {}", fileName, e);
            }
        } catch (IOException e) {
            logger.error("Σφάλμα κατά τη φόρτωση του αρχείου: {}", fileName, e);
            throw new RuntimeException(e);
        }

        return transactions;
    }
}
