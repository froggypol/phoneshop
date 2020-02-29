package com.es.core.service;

import com.es.core.model.CartItemModel;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.data.PhoneData;
import com.es.core.model.AjaxAddingToCartModel;
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

    public static String UPDATE_RESERVED_COUNTER_IN_STOCK = "update stocks set (reserved) = (?)";

    @Resource
    private PhoneService phoneService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public CartModel getCart(HttpSession session) {
        CartModel cart = (CartModel) session.getAttribute("cart");
        return cart;
    }

    @Override
    public void addPhone(AjaxAddingToCartModel ajaxAddingToCartModel, HttpSession httpSession) throws OutOfStockException {
        Long quantity = ajaxAddingToCartModel.getQuantityToAdd();
        Long phoneId = ajaxAddingToCartModel.getIdOfAddingPhone();
        CartItemModel cartItem = new CartItemModel(quantity, phoneService.get(phoneId));
        PhoneModel phoneToAdd = phoneService.get(phoneId);
        CartModel cart = getCart(httpSession);
        List<CartItemModel> cartItemList = cart.getCartItemList();
        CartItemModel checkedCartItem = new CartItemModel(quantity, phoneToAdd);
        if (cartItemList.contains(checkedCartItem)) {
            CartItemModel addedCartItem = cartItemList.get(cartItemList.indexOf(checkedCartItem));
            cart.updateCart(addedCartItem, cartItem);
        } else if (quantity <= phoneToAdd.getStock()) {
            cart.addToCart(cartItem);
        } else {
            throw new OutOfStockException();
        }
        jdbcTemplate.update(UPDATE_RESERVED_COUNTER_IN_STOCK, cartItem.getQuantity());
    }

    @Override
    public void update(Map<Long, PhoneData> items) {
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }
}
