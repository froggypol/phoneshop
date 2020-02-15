package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneExtractor implements ResultSetExtractor<List<Phone>> {

    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Phone> phoneList = new ArrayList();
        while (resultSet.next()) {
            Phone phone = new Phone();
            phone.setId(resultSet.getLong("id"));
            phone.setBrand(resultSet.getString("brand"));
            phone.setModel(resultSet.getString("model"));
            phone.setPrice(resultSet.getBigDecimal("price"));
            phone.setImageUrl(resultSet.getString("imageUrl"));
            Color color = new Color(resultSet.getLong("colorId"), resultSet.getString("code"));
            phone.getColors().add(color);
            if (phoneList.contains(phone)) {
                phoneList.get(phoneList.indexOf(phone)).getColors().add(color);
            } else {
                phoneList.add(phone);
            }
        }
        return phoneList;
    }
}
