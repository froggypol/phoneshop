package com.es.core.model.phone;

import com.es.core.model.exceptions.NotFoundPhoneCustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    @Value("${phones.limit}")
    private int limit;

    @Resource
    private JdbcPhoneDao phoneDao;

    public Phone get(final Long key) throws NotFoundPhoneCustomException {
        Optional<Phone> phone = phoneDao.get(key);
        if (phone.isPresent()) {
            return phone.get();
        } else {
            throw new NotFoundPhoneCustomException();
        }
    }

    public void save(final Phone phoneToSave) {
        phoneDao.save(phoneToSave);
    }

    public List<Phone> findAll(int offset) {
        return phoneDao.findAll(limit, offset);
    }

    public List<Phone> searchFor(String phoneNameQuery, String fieldToSort, String orderToSort) {
        return  phoneDao.searchFor(phoneNameQuery, fieldToSort, orderToSort);
    }

    public List<Phone> searchForPhonesByQuery(String phoneNameQuery) {
        return phoneDao.searchForPhonesByNameQuery(phoneNameQuery);
    }
}
