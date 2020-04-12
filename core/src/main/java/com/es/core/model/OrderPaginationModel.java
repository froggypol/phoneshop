package com.es.core.model;

public class OrderPaginationModel {

    private int limit;

    private int offset;

    private int ordersOnPage;

    private int countPages;

    public OrderPaginationModel() {
        this.ordersOnPage = 5;
    }

    public int getOrdersOnPage() {
        return ordersOnPage;
    }

    public void setOrdersOnPage(int ordersOnPage) {
        this.ordersOnPage = ordersOnPage;
    }

    public int getCountPages() {
        return countPages;
    }

    public void setCountPages(int countPages) {
        this.countPages = countPages;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
