package com.es.core.cart;

import com.es.core.model.phone.Phone;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface CartService {

    Cart getCart(HttpSession request);

    void addPhone(Long phoneId, Long quantity, HttpSession httpSession);

    void update(Map<Long, Phone> items);

    void remove(Long phoneId);
}
