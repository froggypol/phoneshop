package com.es.core.service;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.data.PhoneData;
import com.es.core.model.AjaxAddingToCartModel;
import com.es.core.model.CartModel;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface CartService {

    CartModel getCart(HttpSession request);

    void addPhone(AjaxAddingToCartModel ajaxAddingToCartModel, HttpSession httpSession) throws OutOfStockException;

    void update(Map<Long, PhoneData> items);

    void remove(Long phoneId);
}
