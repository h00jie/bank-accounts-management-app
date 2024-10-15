package com.h00jie.beneficiariesaccountsmanager.infrastructure;

import com.h00jie.beneficiariesaccountsmanager.domain.models.Beneficiary;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BeneficiaryCsvLoader {

    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryCsvLoader.class);

    public List<Beneficiary> loadBeneficiaries(String fileName) {
        List<Beneficiary> beneficiaries = new ArrayList<>();
        logger.info("Φόρτωση δικαιούχων από το αρχείο: {}", fileName);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                logger.error("Το αρχείο δεν βρέθηκε: {}", fileName);
                return beneficiaries;
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

                    String beneficiaryId = line[0];
                    String firstName = line[1];
                    String lastName = line[2];

                    logger.debug("Επεξεργασία δικαιούχου: {}, {}, {}", beneficiaryId, firstName, lastName);
                    Beneficiary beneficiary = new Beneficiary(beneficiaryId, firstName, lastName);
                    beneficiaries.add(beneficiary);
                }
                logger.info("Επιτυχής φόρτωση {} δικαιούχων από το αρχείο: {}", beneficiaries.size(), fileName);
            } catch (CsvValidationException e) {
                logger.error("Σφάλμα CSV κατά την ανάγνωση του αρχείου: {}", fileName, e);
            }
        } catch (IOException e) {
            logger.error("Σφάλμα κατά τη φόρτωση του αρχείου: {}", fileName, e);
            throw new RuntimeException(e);
        }

        return beneficiaries;
    }
}
