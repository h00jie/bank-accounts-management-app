package com.h00jie.beneficiariesaccountsmanager.domain.repositories;


import com.h00jie.beneficiariesaccountsmanager.domain.models.Beneficiary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryBeneficiaryRepository implements BeneficiaryRepository {

    private final Map<String, Beneficiary> beneficiaries = new HashMap<>();

    @Override
    public Beneficiary findById(String beneficiaryId) {
        return beneficiaries.get(beneficiaryId);
    }

    @Override
    public void save(Beneficiary beneficiary) {
        beneficiaries.put(beneficiary.getBeneficiaryId(), beneficiary);
    }

    public void saveAll(Iterable<Beneficiary> beneficiaryList) {
        for (Beneficiary beneficiary : beneficiaryList) {
            save(beneficiary);
        }
    }

    public Map<String, Beneficiary> getAll() {
        return beneficiaries;
    }

    public List<Beneficiary> findAll() {
        return new ArrayList<>(beneficiaries.values());
    }
}
