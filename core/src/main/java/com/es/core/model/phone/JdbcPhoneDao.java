package com.es.core.model.phone;

import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
@PropertySource("classpath:properties/application.properties")
public class JdbcPhoneDao implements PhoneDao {

    private static final String PHONES_WITH_COLORS_QUERY = "select phones.id, phones.brand, phones.model, phones.price, phones.displaySizeInches, phones.imageUrl," +
                                                           " phones.description, colors.code, phone2color.colorId " +
                                                           "from phones " +
                                                           "join phone2color on phones.id=phone2color.phoneId " +
                                                           "join colors on phone2color.colorId=colors.id";

    private static final String PHONES_WITH_COLORS_AND_LIMIT_OFFSET_QUERY = PHONES_WITH_COLORS_QUERY + "  limit ?  offset ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        return  getPhoneListWithColors().stream()
                                        .filter(phone -> phone.getId().equals(key))
                                        .findAny();
    }

    private List<Phone> getPhoneListWithColors() {
        return jdbcTemplate.query(PHONES_WITH_COLORS_QUERY, new PhoneExtractor());
    }

    public void save(final Phone phoneToSave) {
        List<Phone> phoneList = getPhoneListWithColors();
        if (phoneList.contains(phoneToSave)) {
            jdbcTemplate.update("update phones set (phones.price) = (?)", phoneToSave.getPrice());
            int index = phoneList.indexOf(phoneToSave);
            updateColorsDataBases(phoneToSave, phoneList.get(index));
        } else {
            jdbcTemplate.update("insert into phones (brand, model, price, imageUrl) values (?, ?, ?, ?)",
                    phoneToSave.getBrand(), phoneToSave.getModel(), phoneToSave.getPrice(), phoneToSave.getImageUrl());
            insertColorsToNewPhoneIfExists(phoneToSave);
        }
    }

    private void insertColorsToNewPhoneIfExists(Phone phoneToSave) {
        for (Color currentColor : phoneToSave.getColors()) {
            jdbcTemplate.update("insert into phone2color values (?, ?)", phoneToSave.getId(), currentColor.getId());
        }
    }

    private void updateColorsDataBases(Phone phoneToSave, Phone savedPhone) {
        deleteUnusedColorsToSavedPhone(savedPhone, phoneToSave);
        insertNewColorsToSavedPhone(savedPhone, phoneToSave);
    }

    private void deleteUnusedColorsToSavedPhone(Phone savedPhone, Phone phoneToSave) {
        for (Color currentColor : savedPhone.getColors()) {
            if (!phoneToSave.getColors().contains(currentColor)) {
                jdbcTemplate.update("delete from phone2color where colorId = ? and phoneId = ?", currentColor.getId(), phoneToSave.getId());
            }
        }
    }

    private void insertNewColorsToSavedPhone(Phone savedPhone, Phone phoneToSave) {
        for (Color currentColor : phoneToSave.getColors()) {
            if (!savedPhone.getColors().contains(currentColor)) {
                jdbcTemplate.update("insert into phone2color values (?, ?)", phoneToSave.getId(), currentColor.getId());
            }
        }
    }

    public List<Phone> findAll(int limit, int offset) {
        return jdbcTemplate.query(PHONES_WITH_COLORS_AND_LIMIT_OFFSET_QUERY, new Object[]{limit, offset}, new PhoneExtractor());
    }

    public List<Phone> searchFor(String productName, String fieldToSort, String orderToSort) {
        List<Phone> phoneList = getPhoneListWithColors();
        List<Phone> res = new ArrayList<>();
        Sort sort = new Sort(phoneList);
        if (fieldToSort == null && orderToSort == null) {
            return phoneList;
        } else if (productName != null && !productName.equals("")) {
            sort.setListToSort(searchForPhonesByNameQuery(productName));
            res = sort.getListToSort();
        }
        return sort.sortProductsList(fieldToSort, orderToSort);
    }
    public List<Phone> searchForPhonesByNameQuery(String phoneNameQuery) {
        List<Phone> phoneList = getPhoneListWithColors();
        List<Phone> res = new ArrayList<>();
        if (phoneNameQuery == null || phoneNameQuery == "") {
            return getPhoneListWithColors();
        } else {
            String[] list = Pattern.compile(" ").split(phoneNameQuery);
            phoneList.forEach(product -> {
                Arrays.stream(list).forEach(word -> {
                    if (word == null || product.getModel().contains(word) && !res.contains(product)) {
                        res.add(product);
                    }
                });
            });
            return res;
        }
    }
}
