package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.StockQueryForDatabase;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    PhoneService phoneService;

    @Resource
    JdbcTemplate jdbcTemplate;

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart)session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity, HttpSession httpSession) {
        CartItem cartItem = new CartItem(quantity, phoneService.get(phoneId));
        Phone phoneToAdd = phoneService.get(phoneId);
        Cart cart = getCart(httpSession);
        Stock stockOfAddingPhone = (Stock)jdbcTemplate.query(StockQueryForDatabase.SELECT_NECESSARY_PHONE_WITH_STOCK,
                new Object[]{phoneToAdd.getId()}, new BeanPropertyRowMapper<>(Stock.class)).get(0);
        List<CartItem> cartItemList = cart.getCartItemList();
        if (cartItemList.contains(phoneToAdd)) {
            CartItem addedCartItem = cartItemList.get(cartItemList.indexOf(phoneToAdd));
            cart.updateCart(addedCartItem, cartItem, stockOfAddingPhone);
        } else if (quantity <= stockOfAddingPhone.getStock()){
            cart.addToCart(cartItem);
        }
        jdbcTemplate.update(StockQueryForDatabase.UPDATE_RESERVED_COUNTER_IN_STOCK, cartItem.getQuantity());
    }

    @Override
    public void update(Map<Long, Phone> items) {
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }
}
