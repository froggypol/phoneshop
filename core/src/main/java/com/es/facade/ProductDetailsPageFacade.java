package com.es.facade;

import com.es.core.model.PhoneModel;
import com.es.service.PhoneService;
import org.springframework.ui.Model;

import javax.annotation.Resource;

public class ProductDetailsPageFacade {

    @Resource
    private PhoneService phoneService;

    public void getProductDetailPage(Long phoneId, Model model) {
        PhoneModel phoneModel = phoneService.get(phoneId);
        model.addAttribute("phone", phoneModel);
    }
}
