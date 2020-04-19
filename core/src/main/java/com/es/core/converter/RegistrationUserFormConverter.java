package com.es.core.converter;

import com.es.core.form.SignInUserForm;
import com.es.core.model.SignInModel;
import com.es.core.populator.impl.RegistrationUserFormPopulator;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class RegistrationUserFormConverter implements Converter<SignInUserForm, SignInModel> {

    private List<RegistrationUserFormPopulator> populators;

    @Override
    public SignInModel convert(SignInUserForm userForm) {
        SignInModel model = new SignInModel();
        populators.forEach(populator -> populator.populate(userForm, model));
        return model;
    }

    public List<RegistrationUserFormPopulator> getPopulators() {
        return populators;
    }

    public void setPopulators(List<RegistrationUserFormPopulator> populators) {
        this.populators = populators;
    }
}
