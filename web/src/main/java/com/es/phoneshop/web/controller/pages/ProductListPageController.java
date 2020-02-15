package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductListPageController {
    @Resource
    private PhoneService phoneService;

    @RequestMapping(method = RequestMethod.GET, value = "/productList")
    public String showProductList(Model model) {
        model.addAttribute("phones", phoneService.findAll(0));
        return "productList";
    }
}
