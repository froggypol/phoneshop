package com.es.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderPaginationDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String COUNT_PAGES_IN_ADMIN_PAGE = "select count(distinct orderTable.orderId) from (select * from orders) as orderTable";

    public double countPages() {
        double counter = jdbcTemplate.queryForObject(COUNT_PAGES_IN_ADMIN_PAGE, Double.class);
        return counter;
    }

}
