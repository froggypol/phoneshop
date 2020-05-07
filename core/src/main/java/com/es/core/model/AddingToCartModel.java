package com.es.core.model;

public class AddingToCartModel {

    private Long idOfAddingPhone;

    private Long quantityToAdd;

    public AddingToCartModel() {
        this.idOfAddingPhone = 1L;
        this.quantityToAdd = 1L;
    }

    public AddingToCartModel(Long idOfAddingPhone, Long quantityToAdd) {
        this.idOfAddingPhone = idOfAddingPhone;
        this.quantityToAdd = quantityToAdd;
    }

    public Long getIdOfAddingPhone() {
        return idOfAddingPhone;
    }

    public Long getQuantityToAdd() {
        return quantityToAdd;
    }

    public void setIdOfAddingPhone(Long idOfAddingPhone) {
        this.idOfAddingPhone = idOfAddingPhone;
    }

    public void setQuantityToAdd(Long quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }
}
