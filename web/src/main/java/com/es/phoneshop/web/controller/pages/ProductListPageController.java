package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import com.es.phoneshop.web.controller.PhonePageListHolderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductListPageController {

    @Resource
    private PhoneService phoneService;

    @Resource
    private PhonePageListHolderService phonePageListHolder;

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET, value = "/productList")
    public String showProductList(@RequestParam(value = "query", required = false) String phoneNameQuery,
                                  @RequestParam(value = "order", required = false, defaultValue = "asc") String orderToSort,
                                  @RequestParam(value = "fieldToSort", required = false, defaultValue = "brand") String fieldToSort,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  Model model, HttpSession session) {
        Cart cart = cartService.getCart(session);
        List<Phone> phoneList = phoneService.searchFor(phoneNameQuery, fieldToSort, orderToSort);
        model.addAttribute("phones", phoneList);

        phonePageListHolder.listPages(phoneNameQuery, fieldToSort, orderToSort, model, page);

        return "productList";
    }

}
