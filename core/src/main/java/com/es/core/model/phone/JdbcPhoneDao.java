package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {

    private String SQL_statement = "select phones.id, phones.brand, phones.model, phones.price, phones.imageUrl, colors.code, colors.id " +
                                   "from phones " +
                                   "join phone2color on phones.id = phone2color.phoneId " +
                                   "join colors on phone2color.colorId = colors.id";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        List<Phone> resultList = jdbcTemplate.query(SQL_statement, new PhoneRowMapper());
        Optional<Phone> expectedPhone = Optional.of(resultList.get(Math.abs(key.intValue() - 1000)));
        return expectedPhone;
    }

    public void save(final Phone phoneToSave) {
        List<Phone> phoneList = jdbcTemplate.query(SQL_statement, new PhoneRowMapper());
        int index = phoneList.indexOf(phoneToSave);
        if (index >= 0) {
            jdbcTemplate.update("update phones set ( phones.price) = ( ?)",
                    phoneToSave.getPrice());
        } else if (index < 0) {
            jdbcTemplate.update("insert into phones (brand, model, price, imageUrl) values (?, ?, ?)",
                    phoneToSave.getBrand(), phoneToSave.getModel(), phoneToSave.getPrice());
        }
        updateColorsDataBases(phoneToSave);
    }

    private void updateColorsDataBases(Phone phoneToSave) {
        List<Color> colorList = jdbcTemplate.query("select * from colors", new BeanPropertyRowMapper<>(Color.class));
        for (Color currentColor : phoneToSave.getColors()) {
            if (!colorList.contains(currentColor)) {
                jdbcTemplate.update("insert into colors (code) values (?)", currentColor.getCode());
                String codeColor = currentColor.getCode();
                Color colorWithId = jdbcTemplate.query("select * from colors where colors.code = ?", new Object[]{codeColor},
                        new BeanPropertyRowMapper<>(Color.class)).get(0);
                jdbcTemplate.update("insert into phone2color (phoneId, colorId) values (?, ?)",
                        phoneToSave.getId().intValue(), colorWithId.getId().intValue());
            }
        }
    }

    public List<Phone> findAll(int offset, int limit) {
        PhoneRowMapper phoneRowMapper = new PhoneRowMapper();
        jdbcTemplate.query(SQL_statement, phoneRowMapper);
        return phoneRowMapper.getFilledPhones();
    }
}
