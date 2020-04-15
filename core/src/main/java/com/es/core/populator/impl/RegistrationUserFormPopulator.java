package com.es.core.populator.impl;

import com.es.core.form.SignInUserForm;
import com.es.core.model.SignInModel;
import com.es.core.populator.interfaces.Populator;

public class RegistrationUserFormPopulator implements Populator<SignInUserForm, SignInModel> {

    @Override
    public void populate(SignInUserForm source, SignInModel target) {
        target.setPassword(source.getPassword());
        target.setUsername(source.getUsername());
    }

}
