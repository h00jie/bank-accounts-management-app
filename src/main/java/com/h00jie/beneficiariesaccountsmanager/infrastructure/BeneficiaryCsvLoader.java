package com.h00jie.beneficiariesaccountsmanager.infrastructure;

import com.h00jie.beneficiariesaccountsmanager.domain.models.Beneficiary;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BeneficiaryCsvLoader {

    public List<Beneficiary> loadBeneficiaries(String fileName) {
        List<Beneficiary> beneficiaries = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            assert inputStream != null;
            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

                String[] line;
                boolean isFirstLine = true;
                while ((line = reader.readNext()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }

                    String beneficiaryId = line[0];
                    String firstName = line[1];
                    String lastName = line[2];

                    Beneficiary beneficiary = new Beneficiary(beneficiaryId, firstName, lastName);
                    beneficiaries.add(beneficiary);
                }
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return beneficiaries;
    }
}
