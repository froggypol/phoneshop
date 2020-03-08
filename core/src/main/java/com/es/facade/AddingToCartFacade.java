package com.es.facade;

import com.es.core.response.AddToCartResponseBody;
import com.es.core.converter.AjaxAddingToCartConverter;
import com.es.core.form.AddingToCartForm;
import com.es.core.model.AddingToCartModel;
import com.es.core.model.CartModel;
import com.es.service.CartService;
import com.es.core.exceptions.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class AddingToCartFacade {

    @Resource
    private CartService cartService;

    @Resource
    private AjaxAddingToCartConverter ajaxAddingToCartConverter;

    public ResponseEntity updateCartViaAjax(AddingToCartForm cartForm) throws OutOfStockException {
        AddToCartResponseBody ajaxResponseBody = new AddToCartResponseBody();
        AddingToCartModel ajaxAddingToCartModel = ajaxAddingToCartConverter.convert(cartForm);
        CartModel cartModel = cartService.getCart();

        cartService.addPhone(ajaxAddingToCartModel);

        cartModel.setTotalCost(cartService.getCart().getTotalCost());
        cartModel.setTotalQuantity(cartService.getCart().getTotalQuantity());

        ajaxResponseBody.setQuantityToAdd(BigDecimal.valueOf(cartModel.getTotalQuantity()));
        ajaxResponseBody.setTotalCost(cartModel.getTotalCost());
        ajaxResponseBody.setTotalQuantity(BigDecimal.valueOf(cartModel.getTotalQuantity()));
        return new ResponseEntity<>(ajaxResponseBody, HttpStatus.OK);
    }

    public ResponseEntity updatingInvalidInput() {
        AddToCartResponseBody ajaxResponseBody = new AddToCartResponseBody();
        ajaxResponseBody.setMsg("Invalid quantity input");
        return new ResponseEntity<String>(ajaxResponseBody.getMsg(), HttpStatus.BAD_REQUEST);
    }
}
