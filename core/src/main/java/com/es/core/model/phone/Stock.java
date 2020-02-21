package com.es.core.model.phone;

import javax.annotation.Resource;

public class Stock {

    @Resource
    PhoneService phoneService;

    private Phone phone;

    private Integer stock;

    private Integer reserved;

    public Stock() {
    }

    public Stock(String phoneId, int stock, int reserved) {
        this.phone = phoneService.get(Long.valueOf(phoneId));
        this.stock = stock;
        this.reserved = reserved;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }
}
