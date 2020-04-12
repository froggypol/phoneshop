package com.es.service;

import com.es.core.dao.OrderPaginationDao;
import com.es.core.model.OrderModel;
import com.es.core.model.OrderPaginationModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderPaginationService {

    @Resource
    private HttpSession httpSession;

    @Resource
    private OrderService orderService;

    @Resource
    private OrderPaginationDao orderPaginationDao;

    public List<OrderModel> listPages(int currentPage) {
        List<OrderModel> orderList;
        OrderPaginationModel paginationModel = new OrderPaginationModel();
        int pageNumber = countPages(currentPage, paginationModel);
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

    private int countPages(int currentPage, OrderPaginationModel orderPaginationModel) {
        double counter;
        int ordersOnPage = orderPaginationModel.getOrdersOnPage();
        int limit = ordersOnPage;
        int offset = ordersOnPage * (currentPage - 1);
        orderPaginationModel.setLimit(limit);
        orderPaginationModel.setOffset(offset);
        counter = orderPaginationDao.countPages();
        orderPaginationModel.setCountPages((int) Math.ceil(counter / ordersOnPage));
        httpSession.setAttribute("maxPagesForOrder", orderPaginationModel.getCountPages());
        return ordersOnPage;
    }
    
}
