package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.OrderModel;
import com.es.facade.OrdersAdminPageFacade;
import com.es.service.OrderPaginationService;
import com.es.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class OrdersAdminPageController {

    @Resource
    private OrdersAdminPageFacade adminPageFacade;

    @GetMapping(value = "/admin/orders")
    public String getOrders(@RequestParam(name = "pageForOrder", required = false, defaultValue = "1") Integer currentPage, Model model) {
        List<OrderModel> orderModels = adminPageFacade.getOrders(currentPage);
        model.addAttribute("orderList", orderModels);
        return "ordersAdmin";
    }

    @GetMapping(value = "/admin/orders/{orderNumber}")
    public String getOrderInfo(@PathVariable(name = "orderNumber") Integer orderNumber, Model model) {
        OrderModel orderModel = adminPageFacade.getOrderInfo(orderNumber);
        model.addAttribute("orderModelInfo", orderModel);
        return "orderInfo";
    }

    @PostMapping(value = "/admin/orders/{orderNumber}")
    public String changeOrderStatus(@PathVariable(name = "orderNumber") Integer orderNumber,
                                    @RequestParam(name = "status") String status, Model model) {
        OrderModel orderModel = adminPageFacade.changeOrderStatus(orderNumber, status);
        model.addAttribute("orderModelInfo", orderModel);
        return "redirect:/admin/orders/" + orderNumber;
    }
}
