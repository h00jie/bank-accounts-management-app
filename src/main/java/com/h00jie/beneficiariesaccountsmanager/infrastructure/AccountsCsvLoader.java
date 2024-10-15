package com.h00jie.beneficiariesaccountsmanager.infrastructure;

import com.h00jie.beneficiariesaccountsmanager.domain.models.Account;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AccountsCsvLoader {

    public List<Account> loadAccounts(String fileName) {
        List<Account> accounts = new ArrayList<>();

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

                    String accountId = line[0];
                    String beneficiaryId = line[1];

                    Account account = new Account(accountId, beneficiaryId);
                    accounts.add(account);
                }
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }
}
