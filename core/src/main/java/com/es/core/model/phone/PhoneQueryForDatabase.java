package com.es.core.model.phone;

import org.springframework.stereotype.Component;

@Component
public class PhoneQueryForDatabase {

    private static final String PHONES_WITH_COLORS_QUERY = "select * from phones " +
            "join phone2color on phones.id=phone2color.phoneId " +
            "join colors on phone2color.colorId=colors.id " +
            "left join stocks on stocks.phoneId=phones.id where stocks.stock>0";


    private static final String PHONES_WITH_COLORS_AND_LIMIT_OFFSET_QUERY = PHONES_WITH_COLORS_QUERY + "  limit ?  offset ?";

    public String getPhonesWithColorsQuery() {
        return PHONES_WITH_COLORS_QUERY;
    }

    public String getPhonesWithColorsAndLimitOffsetQuery() {
        return PHONES_WITH_COLORS_AND_LIMIT_OFFSET_QUERY;
    }
}
