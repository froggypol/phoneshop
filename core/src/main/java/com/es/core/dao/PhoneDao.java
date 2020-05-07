package com.es.core.dao;

import com.es.core.model.PhoneModel;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {

    Optional<PhoneModel> get(Long key);

    Optional<PhoneModel> getByModel(String model);

    void save(PhoneModel phone);

    List<PhoneModel> findAll(int limit, int offset);

}
