package com.h00jie.beneficiariesaccountsmanager.infrastructure;


import com.h00jie.beneficiariesaccountsmanager.domain.models.Money;
import com.h00jie.beneficiariesaccountsmanager.domain.models.Transaction;
import com.h00jie.beneficiariesaccountsmanager.domain.models.TransactionType;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionCsvLoader {

    public List<Transaction> loadTransactions(String fileName) {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

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

                    String transactionId = line[0];
                    String accountId = line[1];
                    BigDecimal amountValue = new BigDecimal(line[2]);
                    Money amount = new Money(amountValue);
                    TransactionType type = TransactionType.valueOf(line[3].toUpperCase());
                    LocalDate date = LocalDate.parse(line[4], formatter);

                    Transaction transaction = new Transaction(
                            transactionId,
                            accountId,
                            amount,
                            type,
                            date
                    );

                    transactions.add(transaction);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
