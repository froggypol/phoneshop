package com.es.service;

import com.es.core.dao.UserDao;
import com.es.core.model.SignInModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    public void saveUser(SignInModel userModel) {
        userDao.saveUser(userModel);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
    }

}
