package com.es.core.converter;


import com.es.core.data.PhoneData;
import com.es.core.model.PhoneModel;
import com.es.core.populator.impl.PhoneModelPopulator;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class PhoneDataConverter implements Converter<PhoneModel, PhoneData> {

    private List<PhoneModelPopulator> phoneModelPopulators;

    @Override
    public PhoneData convert(PhoneModel phoneModel) {
        PhoneData phoneData = new PhoneData();
        phoneModelPopulators.stream().forEach(populator -> populator.populate(phoneModel, phoneData));
        return phoneData;
    }

    public List<PhoneModelPopulator> getPopulators() {
        return phoneModelPopulators;
    }

    public void setPopulators(List<PhoneModelPopulator> populators) {
        this.phoneModelPopulators = populators;
    }

}
