package com.es.facade;

import com.es.core.converter.RegistrationUserFormConverter;
import com.es.core.form.SignInUserForm;
import com.es.core.model.SignInModel;
import com.es.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

public class RegistrationFacade {

    @Resource
    private UserService userService;

    @Resource
    private RegistrationUserFormConverter userFormConverter;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    public void saveUser(SignInUserForm signInUserForm) {
        signInUserForm.setPassword(passwordEncoder.encode(signInUserForm.getPassword()));
        SignInModel signInModel = userFormConverter.convert(signInUserForm);
        userService.saveUser(signInModel);
    }
}
