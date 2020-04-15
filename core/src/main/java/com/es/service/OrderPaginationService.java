package com.es.service;

import com.es.core.dao.OrderPaginationDao;
import com.es.core.model.OrderModel;
import com.es.core.model.OrderPaginationModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@PropertySource("classpath:properties/application.properties")
public class OrderPaginationService {

    @Value("#{new java.lang.Integer(${orders.limit})}")
    private Integer limit;

    @Resource
    private HttpSession httpSession;

    @Resource
    private OrderService orderService;

    @Resource
    private OrderPaginationDao orderPaginationDao;

    public List<OrderModel> listPages(int currentPage) {
        List<OrderModel> orderList;
        OrderPaginationModel paginationModel = new OrderPaginationModel();
        double pageNumber = countPages(currentPage, paginationModel);
        paginationModel.setCountPages(pageNumber);
        if (currentPage > 0 && currentPage < pageNumber) {
            httpSession.setAttribute("pageForOrder", currentPage);
        }
        if (currentPage <= 0) {
            currentPage = 1;
            httpSession.setAttribute("pageForOrder", currentPage);
        }
        if (currentPage >= pageNumber) {
            httpSession.setAttribute("pageForOrder", (pageNumber - 1));
        }

        orderList = orderService.findAll(paginationModel.getLimit(), paginationModel.getOffset());
        return orderList;
    }

    private double countPages(int currentPage, OrderPaginationModel orderPaginationModel) {
        double counter;
        orderPaginationModel.setLimit(limit);
        int offset = limit * (currentPage - 1);
        orderPaginationModel.setLimit(limit);
        orderPaginationModel.setOffset(offset);
        counter = orderPaginationDao.countPages();
        orderPaginationModel.setCountPages((int) Math.ceil(counter / limit));
        httpSession.setAttribute("maxPagesForOrder", orderPaginationModel.getCountPages());
        return counter;
    }
    
}
