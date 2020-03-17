package com.es.core.populator.impl;

import com.es.core.dto.CartInfoDTO;
import com.es.core.dto.CartPageDTO;
import com.es.core.model.CartPageModel;
import com.es.core.populator.interfaces.Populator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPageDTOToCartModelPopulator implements Populator<CartPageDTO, CartPageModel> {

    @Override
    public void populate(CartPageDTO pageDTO, CartPageModel model) {
        model.setCartInfoModels(getMapOfPhoneQuantities(pageDTO.getCartInfoDTOList()));
    }

    private Map<Long, Long> getMapOfPhoneQuantities(List<CartInfoDTO> cartItems) {
        Map<Long, Long> resultMap = new HashMap<>();
        for(CartInfoDTO cartItem : cartItems) {
            resultMap.put(cartItem.getProductId(), Long.valueOf(cartItem.getQuantityToAdd()));
        }
        return resultMap;
    }
}
