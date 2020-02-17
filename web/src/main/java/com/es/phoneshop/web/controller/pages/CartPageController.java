package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller

public class CartPageController {

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.PUT, value = "/cart/add&quantity={quantity}&productId={productId}")
    public void updateCart(HttpSession httpSession,
                        @NotNull @PathVariable("quantity") int quantity,
                        @NotNull @PathVariable("productId") Long productId) {
        //cartService.getCart(httpSession);


    }

    @RequestMapping(method = RequestMethod.GET, value = "/cart")
    public void getCart() {
        cartService.update(null);
    }
}
