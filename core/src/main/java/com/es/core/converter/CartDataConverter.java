package com.es.core.converter;

import com.es.core.model.CartModel;
import com.es.core.data.CartData;
import com.es.core.populator.interfaces.Populator;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class CartDataConverter implements Converter<CartModel, CartData> {

    private List<Populator<CartModel, CartData>> cartPopulators;

    @Override
    public CartData convert(CartModel cart) {
        CartData cartData = new CartData();
        cartPopulators.stream().forEach(populator -> populator.populate(cart, cartData));
        return cartData;
    }

    public List<Populator<CartModel, CartData>> getCartPopulators() {
        return cartPopulators;
    }

    public void setCartPopulators(List<Populator<CartModel, CartData>> cartPopulators) {
        this.cartPopulators = cartPopulators;
    }
}
