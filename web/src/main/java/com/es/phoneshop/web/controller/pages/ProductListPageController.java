package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductListPageController {

    @Resource
    private PhoneService phoneService;

    @RequestMapping(value = "/productList")
    public String showProductList2(@RequestParam(value = "query", required = false) String phoneNameQuery,
                                   @RequestParam(value = "order", required = false) String orderToSort,
                                   @RequestParam(value = "fieldToSort", required = false) String fieldToSort,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   Model model) {
        model.addAttribute("phones", phoneService.searchFor(phoneNameQuery, fieldToSort, orderToSort));
        List<Phone> phoneList = phoneService.searchFor(phoneNameQuery, fieldToSort, orderToSort);
        PagedListHolder<Phone> phonePagedListHolder = new PagedListHolder<>(phoneList);
        phonePagedListHolder.setPageSize(9);
        model.addAttribute("maxPages", phonePagedListHolder.getPageCount());
        if (page == null || page < 1 || page.intValue() > phonePagedListHolder.getPageCount()) {
            page = 1;
        }
        model.addAttribute("page", page);
        if (page == null || page < 1 || page.intValue() > phonePagedListHolder.getPageCount()) {
            phonePagedListHolder.setPage(0);
            model.addAttribute("phones", phonePagedListHolder);
        } else if (page.intValue() <= phonePagedListHolder.getPageCount()) {
            phonePagedListHolder.setPage(page - 1);
            model.addAttribute("phones", phonePagedListHolder);
        }
        return "productList";
    }

}
