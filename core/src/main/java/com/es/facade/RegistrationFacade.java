package com.es.facade;

import com.es.core.converter.RegistrationUserFormConverter;
import com.es.core.form.SignInUserForm;
import com.es.core.model.SignInModel;
import com.es.service.UserService;

import javax.annotation.Resource;

public class RegistrationFacade {

    @Resource
    private UserService userService;

    @Resource
    private RegistrationUserFormConverter userFormConverter;

    public void saveUser(SignInUserForm signInUserForm) {
        SignInModel signInModel = userFormConverter.convert(signInUserForm);
        userService.saveUser(signInModel);
    }
}
