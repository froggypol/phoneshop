package com.es.core.model;

import java.util.HashMap;
import java.util.Map;

public class CartPageModel {

    private Map<Long, Long> cartInfoModels;

    public CartPageModel() {
        cartInfoModels = new HashMap<>();
    }

    public Map<Long, Long> getCartInfoModels() {
        return cartInfoModels;
    }

    public void setCartInfoModels(Map<Long, Long> cartInfoModels) {
        this.cartInfoModels = cartInfoModels;
    }
}
