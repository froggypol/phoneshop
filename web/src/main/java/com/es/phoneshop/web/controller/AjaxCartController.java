package com.es.phoneshop.web.controller;

import com.es.core.DTO.CartInfoDTO;
import com.es.core.facade.AjaxAddingToCartFacade;
import com.es.core.exceptions.OutOfStockException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class AjaxCartController {

    @Resource
    private AjaxAddingToCartFacade ajaxAddingToCartFacade;

    @RequestMapping(value = "/ajaxCart", method = RequestMethod.POST)
    public ResponseEntity updateCartViaAjax(@Valid @RequestBody CartInfoDTO cartInfo, BindingResult bindingResult, HttpSession session) throws OutOfStockException {
        if (!bindingResult.hasErrors()) {
            return ajaxAddingToCartFacade.updateCartViaAjax(cartInfo, session);
        } else {
            return  ajaxAddingToCartFacade.updatingInvalidInput();
        }
    }
}
