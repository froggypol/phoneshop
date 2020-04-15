package com.es.core.dao;

import com.es.core.model.SignInModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String SAVE_USER_QUERY = "insert into users values (?, ?, ?)";

    private static final String SAVE_USER_ROLE_QUERY = "insert into user_roles values (?, ?)";

    public void saveUser(SignInModel userModel) {
        jdbcTemplate.update(SAVE_USER_QUERY, new Object[]{userModel.getUsername(), userModel.getPassword(), true});
        jdbcTemplate.update(SAVE_USER_ROLE_QUERY, new Object[]{userModel.getUsername(), "ROLE_USER"});
    }

}
