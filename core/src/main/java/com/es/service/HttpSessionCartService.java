package com.es.service;

import com.es.core.model.CartItemModel;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.data.PhoneData;
import com.es.core.model.AddingToCartModel;
import com.es.core.model.CartModel;
import com.es.core.model.PhoneModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    private HttpSession httpSession;

    @Resource
    private PhoneService phoneService;

    @Override
    public CartModel getCart() {
        CartModel cart = (CartModel) httpSession.getAttribute("cart");
        return cart;
    }

    @Override
    public void addPhone(AddingToCartModel ajaxAddingToCartModel) throws OutOfStockException {
        Long quantity = ajaxAddingToCartModel.getQuantityToAdd();
        Long phoneId = ajaxAddingToCartModel.getIdOfAddingPhone();
        CartItemModel cartItem = new CartItemModel(quantity, phoneService.get(phoneId));
        PhoneModel phoneToAdd = phoneService.get(phoneId);
        CartModel cart = getCart();
        List<CartItemModel> cartItemList = cart.getCartItems();
        CartItemModel checkedCartItem = new CartItemModel(quantity, phoneToAdd);
        if (cartItemList.contains(checkedCartItem)) {
            CartItemModel addedCartItem = cartItemList.get(cartItemList.indexOf(checkedCartItem));
            if (quantity <= phoneToAdd.getStock() - addedCartItem.getQuantity()){
                cart.updateCart(cartItem, addedCartItem);
            }
        } else if (!cartItemList.contains(checkedCartItem) && quantity <= phoneToAdd.getStock()) {
            cart.addToCart(cartItem);
        } else {
            throw new OutOfStockException();
        }
    }

    @Override
    public void update(Map<Long, PhoneData> items) {
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }
}
