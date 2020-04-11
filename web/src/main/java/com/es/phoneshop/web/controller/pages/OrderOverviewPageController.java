package com.es.phoneshop.web.controller.pages;

import com.es.core.model.OrderModel;
import com.es.facade.OrderOverviewPageFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
public class OrderOverviewPageController {

    @Resource
    private OrderOverviewPageFacade orderOverviewPageFacade;

    @GetMapping(value = "/orderOverview")
    public String getOrder(@RequestParam(name = "orderId", required = false) UUID orderId, Model model) {
        if (orderId != null && !orderId.equals("")) {
            OrderModel orderModel = orderOverviewPageFacade.getOrder(orderId);
            model.addAttribute("orderModel", orderModel);
            return "orderOverview";
        } else {
            return "/errorPages/noSuchOrder";
        }
    }
}
