package com.h00jie.beneficiariesaccountsmanager.infrastructure;

import com.h00jie.beneficiariesaccountsmanager.domain.models.Account;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AccountsCsvLoader {

    private static final Logger logger = LoggerFactory.getLogger(AccountsCsvLoader.class);

    public List<Account> loadAccounts(String fileName) {
        List<Account> accounts = new ArrayList<>();
        logger.info("Φόρτωση λογαριαςμών από το αρχείο: {}", fileName);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                logger.error("Δεν βρέθηκε το αρχείο: {}", fileName);
                return accounts;
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

                    String accountId = line[0];
                    String beneficiaryId = line[1];

                    logger.debug("Επεξεργασία λογαριασμού: {} για δικαιούχο: {}", accountId, beneficiaryId);

                    Account account = new Account(accountId, beneficiaryId);
                    accounts.add(account);
                }

                logger.info("Επιτυχής φόρτωση {} λογαριασμών από το αρχείο: {}", accounts.size(), fileName);
            } catch (CsvValidationException e) {
                logger.error("Σφάλμα CSV κατά την ανάγνωση του αρχείου: {}", fileName, e);
            }
        } catch (IOException e) {
            logger.error("Σφάλμα κατά τη φόρτωση του αρχείου: {}", fileName, e);
            throw new RuntimeException(e);
        }

        return accounts;
    }
}
