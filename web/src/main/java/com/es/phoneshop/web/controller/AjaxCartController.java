package com.es.phoneshop.web.controller;

import com.es.core.cart.AjaxResponseBody;
import com.es.core.cart.Cart;
import com.es.core.cart.CartInfo;
import com.es.core.cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @RequestMapping(value = "/ajaxCart", method = RequestMethod.POST)
    public ResponseEntity updateCartViaAjax(@Valid @RequestBody CartInfo cartInfo, BindingResult bindingResult, HttpSession session) {
        AjaxResponseBody ajaxResponseBody = new AjaxResponseBody();
        if (!bindingResult.hasErrors()) {
            Cart cart = cartService.getCart(session);

            Long quantityToAdd = cartInfo.getQuantityToAdd();
            Long idOfPhoneToAdd = cartInfo.getProductId();

            cartService.addPhone(idOfPhoneToAdd, quantityToAdd, session);

            cart.setTotalCost(cartService.getCart(session).getTotalCost());
            cart.setTotalQuantity(cartService.getCart(session).getTotalQuantity());

            ajaxResponseBody.setQuantityToAdd(BigDecimal.valueOf(cart.getTotalQuantity()));
            ajaxResponseBody.setTotalCost(cart.getTotalCost());
            ajaxResponseBody.setTotalQuantity(BigDecimal.valueOf(cart.getTotalQuantity()));
            return new ResponseEntity<>(ajaxResponseBody, HttpStatus.OK);
        } else {
            ajaxResponseBody.setMsg("Invalid quantity input");
            return new ResponseEntity<String>(ajaxResponseBody.getMsg(), HttpStatus.BAD_REQUEST);
        }
    }
}
