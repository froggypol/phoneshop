package com.es.core.util.validators;

import com.es.core.model.OrderItemModel;
import com.es.core.model.OrderModel;
import com.es.core.model.PhoneModel;
import com.es.service.PhoneService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderValidator implements Validator {

    @Resource
    private PhoneService phoneService;

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderModel.class == aClass;
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderModel orderModel = (OrderModel)o;
        List<OrderItemModel> orderItemList = new ArrayList<>();
        orderModel.getOrderItems().stream().forEach(orderItemModel -> {
            Long id = orderItemModel.getPhone().getId();
            PhoneModel phoneModel = phoneService.get(id);
            if (orderItemModel.getQuantity() <= phoneModel.getStock()) {
                orderItemList.add(orderItemModel);
            }
        });
        List<OrderItemModel> orderItemsWithOutOfStock = new ArrayList<>();
        for(OrderItemModel orderItemModel : orderModel.getOrderItems()) {
            if (!orderItemList.contains(orderItemModel)) {
                orderItemsWithOutOfStock.add(orderItemModel);
            }
        }
        if (orderItemList.size() != orderModel.getOrderItems().size()) {
            orderItemsWithOutOfStock.forEach( orderItModel -> {
                Long id = orderItModel.getPhone().getId();
                PhoneModel phoneModel = phoneService.get(id);
                errors.reject(orderItModel.getPhone().getId().toString(), "Available stock: " + phoneModel.getStock());
            }
            );
        }
    }
}
