package com.es.phoneshop.web.controller.pages;

import com.es.facade.ProductDetailsPageFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class ProductDetailsPageController {

    @Resource
    private ProductDetailsPageFacade productDetailsPageFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/productDetailsPage/productId={productId}")
    public String getProductDetailPage(@PathVariable(name = "productId", required = false) Long phoneId, Model model) {
        return productDetailsPageFacade.getProductDetailPage(phoneId,  model);
    }
}
