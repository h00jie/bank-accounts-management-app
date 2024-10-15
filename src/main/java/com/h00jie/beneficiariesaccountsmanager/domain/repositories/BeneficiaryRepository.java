package com.h00jie.beneficiariesaccountsmanager.domain.repositories;

import com.h00jie.beneficiariesaccountsmanager.domain.models.Beneficiary;

import java.util.List;

public interface BeneficiaryRepository {

    Beneficiary findById(String beneficiaryId);

    void save(Beneficiary beneficiary);

    List<Beneficiary> findAll();
}
