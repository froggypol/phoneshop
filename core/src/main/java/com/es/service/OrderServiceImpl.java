package com.es.service;

import com.es.core.dao.OrderDao;
import com.es.core.exceptions.NoSuchOrderException;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.CartItemModel;
import com.es.core.model.CartModel;
import com.es.core.model.OrderItemModel;
import com.es.core.model.OrderModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@PropertySource("classpath:properties/application.properties")
public class OrderServiceImpl implements OrderService {

    @Value("#{new java.math.BigDecimal(${delivery.price})}")
    private BigDecimal deliveryPrice;

    @Resource
    private HttpSessionCartService cartService;

    @Resource
    private PhoneService phoneService;

    @Resource
    private OrderDao orderDao;

    @Override
    public OrderModel createOrder(CartModel cart, OrderModel orderModel) {
        orderModel.setId(UUID.randomUUID());
        orderModel.setTotalPrice(cart.getTotalCost());
        orderModel.setDeliveryPrice(deliveryPrice);

        cart.getCartItems().forEach(cartItemModel -> {
            OrderItemModel orderItemModel = new OrderItemModel();
            CartItemModel itemModel = new CartItemModel(cartItemModel.getQuantity(), cartItemModel.getPhone());
            orderItemModel.setId(UUID.randomUUID());
            orderItemModel.setQuantity(itemModel.getQuantity());
            orderItemModel.setPhone(itemModel.getPhone());
            orderItemModel.setOrder(orderModel);
            orderModel.getOrderItems().add(orderItemModel);
        });
        return orderModel;
    }

    @Override
    public void placeOrder(OrderModel order) throws OutOfStockException {
        orderDao.recalculate(order);
        orderDao.saveOrder(order);
        phoneService.updateProductAfterOrder();
        updateCart();
    }

    private void updateCart() {
        CartModel cartModel = cartService.getCart();
        cartModel.setTotalCost(BigDecimal.ZERO);
        cartModel.setTotalQuantity(Long.valueOf(0));
        cartModel.setCartItems(new ArrayList<>());
    }

    public OrderModel getOrderById(UUID id) throws NoSuchOrderException {
        Optional<OrderModel> orderModel = orderDao.getOrderByIdThroughDB(id);
        if (orderModel.isPresent()) {
            return orderModel.get();
        } else {
            throw new NoSuchOrderException();
        }
    }
}
