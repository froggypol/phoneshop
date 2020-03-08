package com.es.service;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.data.PhoneData;
import com.es.core.model.AddingToCartModel;
import com.es.core.model.CartModel;

import java.util.Map;

public interface CartService {

    CartModel getCart();

    void addPhone(AddingToCartModel ajaxAddingToCartModel) throws OutOfStockException;

    void update(Map<Long, PhoneData> items);

    void remove(Long phoneId);
}
