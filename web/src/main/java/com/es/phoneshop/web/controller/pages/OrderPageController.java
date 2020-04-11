package com.es.phoneshop.web.controller.pages;

import com.es.core.exceptions.OutOfStockException;
import com.es.core.form.OrderForm;
import com.es.core.model.OrderModel;
import com.es.core.util.validators.PlaceOrderValidator;
import com.es.facade.OrderPageFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
public class OrderPageController {

    @Resource
    private PlaceOrderValidator placeOrderValidator;

    @Resource
    private OrderPageFacade orderPageFacade;

    @GetMapping(value = "/order")
    public String getOrder(@ModelAttribute(name = "orderForm") OrderForm orderForm) {
        return "order";
    }

    @PostMapping(value = "/order")
    public String placeOrder(@ModelAttribute(name = "orderForm") @Valid OrderForm orderForm, BindingResult bindingResult,
                             Model model) throws OutOfStockException {
        if (!bindingResult.hasErrors()) {
            OrderModel orderModel = orderPageFacade.createOrder(orderForm);
            boolean checkIfInvalidStock = hasErrors(orderModel, bindingResult);
            if (checkIfInvalidStock == true) {
                List<ObjectError> fieldErrors = bindingResult.getAllErrors();
                model.addAttribute("errors", fieldErrors);
            } else {
                orderPageFacade.placeOrder(orderModel);
                model.addAttribute("orderModel", orderModel);
                return "redirect:/orderOverview?orderId=" + orderModel.getId();
            }
        }
        return "order";
    }

    private boolean hasErrors(OrderModel orderModel, BindingResult bindingResult) {
        placeOrderValidator.validate(orderModel, bindingResult);
        return bindingResult.hasErrors();
    }
}
