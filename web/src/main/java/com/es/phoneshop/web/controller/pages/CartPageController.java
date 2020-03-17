package com.es.phoneshop.web.controller.pages;

import com.es.core.dto.CartPageDTO;
import com.es.facade.CartPageFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class CartPageController {

    @Resource
    private CartPageFacade cartPageFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/cart")
    public String getCart(Model model) {
        return cartPageFacade.getCart(model);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/cart")
    public String updateCart(@ModelAttribute("cartPageDTO") @Valid CartPageDTO cartPageDTO, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            model.addAttribute("cartPageDTO", cartPageDTO);
            return cartPageFacade.updateCart(cartPageDTO);
        }
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/cart/deleteCartItem")
    public String deleteCartItemFromCart(@RequestParam (name = "idToDelete", required = false) Long idToDelete) {
        cartPageFacade.deleteCartItemFromCart(idToDelete);
        return "redirect:/cart";
    }
}
