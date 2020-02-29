package com.es.core.form;

public class AjaxAddingToCartForm {

    private Long idOfAddingPhone;
    
    private Long quantityToAdd;

    public AjaxAddingToCartForm() {
        this.idOfAddingPhone = 1L;
        this.quantityToAdd = 1L;
    }

    public AjaxAddingToCartForm(Long productId, Long quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
        this.idOfAddingPhone = productId;
    }

    public Long getIdOfAddingPhone() {
        return idOfAddingPhone;
    }

    public Long getQuantityToAdd() {
        return quantityToAdd;
    }
}
