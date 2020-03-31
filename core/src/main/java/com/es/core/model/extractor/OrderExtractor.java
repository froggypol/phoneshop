package com.es.core.model.extractor;

import com.es.core.model.OrderModel;
import com.es.core.model.PhoneModel;
import com.es.core.model.OrderItemModel;
import com.es.core.model.OrderStatus;
import com.es.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderExtractor implements ResultSetExtractor<List<OrderModel>> {

    private PhoneService phoneService;

    @Autowired
    public OrderExtractor(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Override
    public List<OrderModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<OrderItemModel> orderItems = new ArrayList();
        List<OrderModel> orderModels = new ArrayList<>();
        while (resultSet.next()) {
            OrderModel orderModel = new OrderModel();
            OrderItemModel orderItemModel = new OrderItemModel();

            BigDecimal phoneId = resultSet.getBigDecimal("phoneId");
            PhoneModel phoneModel = phoneService.get(phoneId.longValue());

            Long quantity = resultSet.getLong("quantity");

            orderItemModel.setPhone(phoneModel);
            orderItemModel.setId(UUID.randomUUID());
            orderItemModel.setQuantity(quantity);
            orderModel.getOrderItems().add(orderItemModel);

            orderModel.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
            orderModel.setOtherInfo(resultSet.getString("otherInfo"));
            orderModel.setId(UUID.fromString(resultSet.getString("orderId")));
            orderModel.setNumber(resultSet.getBigDecimal("number"));
            orderModel.setFirstName(resultSet.getString("firstName"));
            orderModel.setLastName(resultSet.getString("lastName"));
            orderModel.setDeliveryAddress(resultSet.getString("address"));
            orderModel.setContactPhoneNo(resultSet.getString("contactPhoneNo"));
            orderModel.setFirstName(resultSet.getString("firstName"));
            orderModel.setDeliveryPrice(resultSet.getBigDecimal("deliveryPrice"));
            orderModel.setSubtotal(resultSet.getBigDecimal("subTotalPrice"));
            orderModel.setTotalPrice(orderModel.getSubtotal().add(orderModel.getDeliveryPrice()));

            if (orderItems.contains(orderItemModel)) {
                orderItems.set(orderItems.indexOf(orderItemModel), orderItemModel);
            } else {
                orderItems.add(orderItemModel);
            }

            orderModel.setOrderItems(orderItems);

            if (!orderModels.contains(orderModel)) {
                orderModels.add(orderModel);
            } else {
                orderModels.set(orderModels.indexOf(orderModel), orderModel);
            }
        }
        return orderModels;
    }
}
