package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.es.core.facade.JdbcPhoneDaoFacade;
import com.es.core.form.ProductListPageForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductListPageController {

    @Resource
    private JdbcPhoneDaoFacade jdbcPhoneDaoFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/productList")
    public String showProductList(@RequestParam(value = "query", required = false) String phoneNameQuery,
                                  @RequestParam(value = "order", required = false, defaultValue = "asc") String orderToSort,
                                  @RequestParam(value = "fieldToSort", required = false, defaultValue = "brand") String fieldToSort,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  Model model, HttpSession session) {
        ProductListPageForm productListPageForm = new ProductListPageForm(phoneNameQuery, fieldToSort, orderToSort, page);
        return jdbcPhoneDaoFacade.showProductList(productListPageForm, model, session);
    }
}
