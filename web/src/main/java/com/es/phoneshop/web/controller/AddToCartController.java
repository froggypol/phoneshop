package com.es.phoneshop.web.controller;

import com.es.facade.AddingToCartFacade;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.form.AddingToCartForm;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class AddToCartController {

    @Resource
    private AddingToCartFacade ajaxAddingToCartFacade;

    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    public ResponseEntity updateCartViaAjax(@RequestBody @Valid AddingToCartForm cartForm, BindingResult bindingResult) throws OutOfStockException {
        return bindingResult.hasErrors() ?
                ajaxAddingToCartFacade.updatingInvalidInput() :
                ajaxAddingToCartFacade.updateCartViaAjax(cartForm);
    }
}
