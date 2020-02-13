package com.es.core.model.phone;

import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Component
@PropertySource("classpath:properties/limitProperty.properties")
public class JdbcPhoneDao implements PhoneDao {

    private String limit;

    private static final String joiningTablesSQLStatement = "select phones.id, phones.brand, phones.model, phones.price, phones.imageUrl, colors.code, colors.id " +
                                                               "from phones " +
                                                               "left join phone2color on phones.id = phone2color.phoneId " +
                                                               "left join colors on phone2color.colorId = colors.id";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource(name="limitProperty")
    private Properties myProperties;

    public Optional<Phone> get(final Long key) {
        List<Phone> resultList = getPhoneListWithColors(joiningTablesSQLStatement);
        Optional<Phone> expectedPhone = resultList.stream()
                                                  .filter(phone -> { return phone.getId().equals(key); })
                                                  .findAny();
        return expectedPhone;
    }

    private List<Phone> getPhoneListWithColors(String sql_statement) {
        return jdbcTemplate.query(sql_statement, new PhoneExtractor());
    }

    public void save(final Phone phoneToSave) {
        List<Phone> phoneList = getPhoneListWithColors(joiningTablesSQLStatement);
        int index = phoneList.indexOf(phoneToSave);
        if (index >= 0) {
            jdbcTemplate.update("update phones set (phones.price) = (?)", phoneToSave.getPrice());
            updateColorsDataBases(phoneToSave, phoneList.get(index));
        } else if (index < 0) {
            jdbcTemplate.update("insert into phones (brand, model, price, imageUrl) values (?, ?, ?)",
                    phoneToSave.getBrand(), phoneToSave.getModel(), phoneToSave.getPrice());
            insertColorsToNewPhoneIfExists(phoneToSave);
        }
    }

    private void insertColorsToNewPhoneIfExists(Phone phoneToSave) {
        for(Color currentColor : phoneToSave.getColors()) {
            jdbcTemplate.update("insert into phone2color values (?, ?)", phoneToSave.getId(), currentColor.getCode());
        }
    }

    private void updateColorsDataBases(Phone phoneToSave, Phone savedPhone) {
        deleteUnusedColorsToSavedPhone(savedPhone, phoneToSave);
        insertNewColorsToSavedPhone(savedPhone, phoneToSave);
    }

    private void deleteUnusedColorsToSavedPhone(Phone savedPhone, Phone phoneToSave) {
        for (Color currentColor : savedPhone.getColors()) {
            if (!phoneToSave.getColors().contains(currentColor)) {
               Color color = jdbcTemplate.query("select * from colors where colors.code = ?", new Object[]{currentColor.getCode()}, new BeanPropertyRowMapper<>(Color.class)).get(0);
                jdbcTemplate.update("delete from phone2color where colorId = ? and phoneId = ?", color.getId(), phoneToSave.getId());
            }
        }
    }

    private void insertNewColorsToSavedPhone(Phone savedPhone, Phone phoneToSave) {
        for (Color currentColor : phoneToSave.getColors()) {
            if (!savedPhone.getColors().contains(currentColor)) {
                jdbcTemplate.update("insert into colors values (?, ?)", currentColor.getId(), currentColor.getCode());
                jdbcTemplate.update("insert into phone2color values (?, ?)", phoneToSave.getId(), currentColor.getId());
            }
        }
    }

    public List<Phone> findAll(int offset) {
        limit = myProperties.getProperty("data.limit");
        return getPhoneListWithColors(joiningTablesSQLStatement + " offset " + offset + " limit " + limit);
    }
}
