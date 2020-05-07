package com.es.service;

import com.es.core.dao.JdbcPhoneDao;
import com.es.core.model.CartModel;
import com.es.core.model.PhoneModel;
import com.es.core.exceptions.NotFoundPhoneCustomException;
import com.es.core.model.ProductListPageParametersModel;
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
    private HttpSessionCartService cartService;

    @Resource
    private JdbcPhoneDao phoneDao;

    public PhoneModel get(final Long key) throws NotFoundPhoneCustomException {
        Optional<PhoneModel> phone = phoneDao.get(key);
        if (phone.isPresent()) {
            return phone.get();
        } else {
            throw new NotFoundPhoneCustomException();
        }
    }

    public PhoneModel getByModel(final String model) {
        Optional<PhoneModel> phone = phoneDao.getByModel(model);
        if (phone.isPresent()) {
            return phone.get();
        } else {
            return null;
        }
    }

    public void updateProductAfterOrder() {
        CartModel cart = cartService.getCart();
        phoneDao.updateAfterOrder(cart);
    }

    public void save(final PhoneModel phoneToSave) {
        phoneDao.save(phoneToSave);
    }

    public List<PhoneModel> findAll(int offset) {
        return phoneDao.findAll(limit, offset);
    }

    public List<PhoneModel> searchFor(ProductListPageParametersModel parametersModel) {
        return  phoneDao.searchFor(parametersModel);
    }

    public List<PhoneModel> searchForPhonesByQuery(ProductListPageParametersModel phoneNameQuery) {
        return phoneDao.searchForPhonesByNameQuery(phoneNameQuery);
    }
}
