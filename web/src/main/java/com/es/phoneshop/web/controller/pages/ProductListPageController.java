package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductListPageController {

    @Resource
    private PhoneService phoneService;

    @RequestMapping(value = "/productList/{query}&{sortField}&{order}")
    public String showProductList(@PathVariable(value = "query", required = false) String phoneNameQuery,
                                  @PathVariable(value = "order", required = false) String orderToSort,
                                  @PathVariable(value = "sortField", required = false) String fieldToSort,
                                  Model model) {
        model.addAttribute("phones", phoneService.searchFor(phoneNameQuery, fieldToSort, orderToSort));
        return "productList";
    }

    @RequestMapping(value = "/productList")
    public String showProductList(@RequestParam(value = "query", required = false) String phoneNameQuery, Model model) {
        model.addAttribute("phones", phoneService.searchForPhonesByQuery(phoneNameQuery));
        return "productList";
    }

}
