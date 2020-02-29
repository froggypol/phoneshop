package com.es.core.facade;

import com.es.core.ajax.response.AjaxResponseBody;
import com.es.core.converter.AjaxAddingToCartConverter;
import com.es.core.DTO.CartInfoDTO;
import com.es.core.form.AjaxAddingToCartForm;
import com.es.core.model.AjaxAddingToCartModel;
import com.es.core.model.CartModel;
import com.es.core.service.CartService;
import com.es.core.exceptions.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class AjaxAddingToCartFacade {

    @Resource
    private CartService cartService;

    @Resource
    private AjaxAddingToCartConverter ajaxAddingToCartConverter;

    public ResponseEntity updateCartViaAjax(CartInfoDTO cartInfo, HttpSession session) throws OutOfStockException {
        AjaxResponseBody ajaxResponseBody = new AjaxResponseBody();
        AjaxAddingToCartForm ajaxAddingToCartForm = new AjaxAddingToCartForm(cartInfo.getProductId(), cartInfo.getQuantityToAdd());
        AjaxAddingToCartModel ajaxAddingToCartModel = ajaxAddingToCartConverter.convert(ajaxAddingToCartForm);
        CartModel cartModel = cartService.getCart(session);

        cartService.addPhone(ajaxAddingToCartModel, session);

        cartModel.setTotalCost(cartService.getCart(session).getTotalCost());
        cartModel.setTotalQuantity(cartService.getCart(session).getTotalQuantity());

        ajaxResponseBody.setQuantityToAdd(BigDecimal.valueOf(cartModel.getTotalQuantity()));
        ajaxResponseBody.setTotalCost(cartModel.getTotalCost());
        ajaxResponseBody.setTotalQuantity(BigDecimal.valueOf(cartModel.getTotalQuantity()));
        return new ResponseEntity<>(ajaxResponseBody, HttpStatus.OK);
    }

    public ResponseEntity updatingInvalidInput() {
        AjaxResponseBody ajaxResponseBody = new AjaxResponseBody();
        ajaxResponseBody.setMsg("Invalid quantity input");
        return new ResponseEntity<String>(ajaxResponseBody.getMsg(), HttpStatus.BAD_REQUEST);
    }
}
