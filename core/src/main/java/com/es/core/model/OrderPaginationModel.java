package com.es.core.model;

public class OrderPaginationModel {

    private Integer limit;

    private Integer offset;

    private double countPages;

    public OrderPaginationModel() {

    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public double getCountPages() {
        return countPages;
    }

    public void setCountPages(double countPages) {
        this.countPages = countPages;
    }
}
