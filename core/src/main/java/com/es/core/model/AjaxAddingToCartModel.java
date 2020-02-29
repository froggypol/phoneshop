package com.es.core.model;

public class AjaxAddingToCartModel {

    private Long idOfAddingPhone;

    private Long quantityToAdd;

    public AjaxAddingToCartModel() {
        this.idOfAddingPhone = 1L;
        this.quantityToAdd = 1L;
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
