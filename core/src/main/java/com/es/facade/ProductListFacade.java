package com.es.facade;

import com.es.core.converter.PhoneDataConverter;
import com.es.core.converter.ProductListPageParametersConverter;
import com.es.core.form.ProductListPageForm;
import com.es.core.data.PhoneData;
import com.es.core.model.PhoneModel;
import com.es.core.model.ProductListPageParametersModel;
import com.es.service.PagingService;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ProductListFacade {

    @Resource
    private PhoneDataConverter phoneDataConverter;

    @Resource
    private ProductListPageParametersConverter parametersConverter;

    @Resource
    private PagingService service;

    public String showProductList(ProductListPageForm pageForm, Model model, HttpSession session) {
        ProductListPageParametersModel parametersModel = parametersConverter.convert(pageForm);
        List<PhoneModel> phoneListModel = service.listPages(parametersModel, model, session);
        List<PhoneData> phoneListData = new ArrayList<>();
        phoneListModel.stream().forEach(phoneModel -> phoneListData.add(phoneDataConverter.convert(phoneModel)));
        model.addAttribute("phones", phoneListData);
        return "productList";
    }
}
