package com.es.facade;

import com.es.core.model.PhoneModel;
import com.es.service.PhoneService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

public class ProductDetailsPageFacade {

    @Resource
    private PhoneService phoneService;

    public String getProductDetailPage(@PathVariable(name = "productId", required = false) Long phoneId, Model model) {
        PhoneModel phoneModel = phoneService.get(phoneId);
        model.addAttribute("phone", phoneModel);
        return "productDetailsPage";
    }

}
