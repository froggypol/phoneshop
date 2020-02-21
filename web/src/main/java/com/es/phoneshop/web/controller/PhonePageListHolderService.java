package com.es.phoneshop.web.controller;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhonePageListHolderService {

    @Resource
    PhoneService phoneService;

    public void listPages(String phoneNameQuery, String fieldToSort, String orderToSort, Model model, Integer page) {
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
    }
}
