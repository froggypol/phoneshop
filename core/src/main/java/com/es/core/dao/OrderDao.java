package com.es.core.dao;

import com.es.core.model.OrderModel;
import com.es.core.model.extractor.OrderExtractor;
import com.es.core.model.OrderStatus;
import com.es.service.PhoneService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private PhoneService phoneService;

    private String GET_ORDER_BY_NUMBER_QUERY = "select orderId from orders where orders.number = ?";

    private String CHANGE_STATUS_QUERY = "update orders set status = (?) where orders.orderId = ?";

    private String GET_ORDER_BY_ID_QUERY = "select * from orders where orders.orderId like ?";

    private String INSERT_ORDER_QUERY = "insert into orders (quantity, orderId, phoneId, firstName, lastName, address," +
            " contactPhoneNo, otherInfo, status, totalPrice, subTotalPrice, deliveryPrice)" +
            " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private String GET_ALL_ORDERS_QUERY = "select * from orders ";

    private OrderDao() {

    }

    public List<OrderModel> getOrders() {
        List<OrderModel> orderList = jdbcTemplate.query(GET_ALL_ORDERS_QUERY, new OrderExtractor(phoneService));
        return orderList;
    }

    public List<OrderModel> findAll(int limit, int offset) {
        List<OrderModel> orderList = jdbcTemplate.query(GET_ALL_ORDERS_QUERY + "limit ? offset ?", new OrderExtractor(phoneService),
                new Object[]{limit, offset});
        return orderList;
    }

    public void saveOrder(OrderModel order) {
        List<OrderModel> orderList = getOrders();
        if (!orderList.contains(order)) {
            order.setStatus(OrderStatus.NEW);
            orderList.add(order);
            order.getOrderItems().forEach(orderItemModel -> {
                        String id = order.getId().toString();
                        Long phoneId = orderItemModel.getPhone().getId() ;
                        jdbcTemplate.update(INSERT_ORDER_QUERY, orderItemModel.getQuantity(),
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
        List<OrderModel> orderModelList = jdbcTemplate.query(GET_ORDER_BY_ID_QUERY, new OrderExtractor(phoneService), "%" + id + "%");
        if (orderModelList.size() == 0) {
            return Optional.ofNullable(null);
        } else {
            return Optional.of(orderModelList.get(0));
        }
    }

    public void recalculate(OrderModel order) {
        order.setTotalPrice(order.getTotalPrice().add(order.getDeliveryPrice()));
    }

    public Optional<OrderModel> getOrderByNumber(Integer orderNumber) {
        UUID orderId = jdbcTemplate.queryForObject(GET_ORDER_BY_NUMBER_QUERY, UUID.class,
                new Object[]{orderNumber});
        return getOrderByIdThroughDB(orderId);
    }

    public OrderModel changeStatus(String status, UUID orderId) {
        jdbcTemplate.update(CHANGE_STATUS_QUERY, new Object[]{(status), orderId});
        return getOrderByIdThroughDB(orderId).get();
    }
}
