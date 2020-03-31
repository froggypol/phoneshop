package com.es.core.dao;

import com.es.core.model.OrderModel;
import com.es.core.model.extractor.OrderExtractor;
import com.es.core.model.OrderStatus;
import com.es.service.PhoneService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderDao {

    private List<OrderModel> orderList;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private PhoneService phoneService;

    private OrderDao() {
        orderList = new ArrayList<>();
    }

    public void saveOrder(OrderModel order) {
        if (!orderList.contains(order)) {
            order.setStatus(OrderStatus.NEW);
            orderList.add(order);
            order.getOrderItems().forEach(orderItemModel -> {
                String id = order.getId().toString();
                Long phoneId = orderItemModel.getPhone().getId() ;
                        jdbcTemplate.update("insert into orders (quantity, orderId, phoneId, firstName, lastName, address," +
                                                " contactPhoneNo, otherInfo, status, totalPrice, subTotalPrice, deliveryPrice)" +
                                                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", orderItemModel.getQuantity(),
                                            id, phoneId, order.getFirstName(), order.getLastName(), order.getDeliveryAddress(),
                                            order.getContactPhoneNo(), order.getOtherInfo(), order.getStatus().toString(),
                                            order.getTotalPrice(),  order.getTotalPrice().subtract(order.getDeliveryPrice()),
                                            order.getDeliveryPrice());
                    }
            );
        }
    }

    public Optional<OrderModel> getOrderByIdThroughDB(UUID orderId)  {
        String id = orderId.toString();
        List<OrderModel> orderModelList = jdbcTemplate.query("select * from orders where orders.orderId like ?",
                new OrderExtractor(phoneService), "%" + id + "%");
        if (orderModelList.size() == 0) {
            return Optional.ofNullable(null);
        } else {
            return Optional.of(orderModelList.get(0));
        }
    }

    public void recalculate(OrderModel order) {
        order.setTotalPrice(order.getTotalPrice().add(order.getDeliveryPrice()));
    }

    public List<OrderModel> getOrderList() {
        return orderList;
    }
}
