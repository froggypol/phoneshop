package com.es.core.converter;

import com.es.core.form.AjaxAddingToCartForm;
import com.es.core.model.AjaxAddingToCartModel;
import com.es.core.populator.impl.AjaxAddingToCartDataPopulator;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class AjaxAddingToCartConverter implements Converter<AjaxAddingToCartForm, AjaxAddingToCartModel> {

    private List<AjaxAddingToCartDataPopulator> ajaxAddingToCartDataPopulators;

    @Override
    public AjaxAddingToCartModel convert(AjaxAddingToCartForm ajaxAddingToCartForm) {
        AjaxAddingToCartModel model = new AjaxAddingToCartModel();
        ajaxAddingToCartDataPopulators.stream().forEach(populator -> populator.populate(ajaxAddingToCartForm, model));
        return model;
    }

    public void setAjaxAddingToCartDataPopulators(List<AjaxAddingToCartDataPopulator> ajaxAddingToCartDataPopulators) {
        this.ajaxAddingToCartDataPopulators = ajaxAddingToCartDataPopulators;
    }
}
