package com.es.core.populator.impl;

import com.es.core.form.AjaxAddingToCartForm;
import com.es.core.model.AjaxAddingToCartModel;
import com.es.core.populator.interfaces.Populator;

public class AjaxAddingToCartDataPopulator implements Populator<AjaxAddingToCartForm, AjaxAddingToCartModel> {

    @Override
    public void populate(AjaxAddingToCartForm data, AjaxAddingToCartModel model) {
        model.setQuantityToAdd(data.getQuantityToAdd());
        model.setIdOfAddingPhone(data.getIdOfAddingPhone());
    }
}
