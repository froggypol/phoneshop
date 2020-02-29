package com.es.core.sorting;
import com.es.core.model.PhoneModel;
import com.es.core.model.ProductListPageParametersModel;
import com.es.core.model.extractor.PhoneExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAndSortInDataBase {

    private static final String PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART = "select * from (select * from phones join stocks on stocks.phoneId=id where stocks.stock>0 order by ";

    private static final String PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART = " limit ? offset ? ) " +
            "as tempPhone left join phone2color as p2c on tempPhone.id=p2c.phoneId " +
            "left join colors on p2c.colorId=colors.id ";

    private static String SEARCHING_PHONES_BY_ALL_PARAMS_NAME_DESC_FIRST_PART = "select * from (select * from phones join stocks on stocks.phoneId=id where stocks.stock>0 " +
            " and lower(phones.model) like (?) or lower(phones.description) like (?) order by ";

    private static String SEARCHING_PHONES_BY_ALL_PARAMS_NAME_DESC_SECOND_PART =  " limit ? offset ? ) " +
            "as tempPhone left join phone2color as p2c on tempPhone.id=p2c.phoneId " +
            "left join colors on p2c.colorId=colors.id ";

    private JdbcTemplate jdbcTempl;

    @Autowired
    public FindAndSortInDataBase(JdbcTemplate jdbcTemplate) {
        this.jdbcTempl = jdbcTemplate;
    }

    public List<PhoneModel> sortProductsList(String fieldToSort, String orderToSort, int limit, int offset) {
        return fieldToSort.equals("displaySizeInches") ?
                sortingOnSize(orderToSort, limit, offset) :
                fieldToSort.equals("brand") ?
                        sortingOnBrand(orderToSort, limit, offset) :
                        fieldToSort.equals("model") ?
                                sortingOnModel(orderToSort, limit, offset) :
                                sortingOnPrice(orderToSort, limit, offset);
    }

    private List<PhoneModel> resultsOfSorting(String query,int limit, int offset) {
        return jdbcTempl.query(query, new Object[]{limit, offset}, new PhoneExtractor());
    }

    private List<PhoneModel> sortingOnSize(String order, int limit, int offset) {
        if (order.equals("asc")) {
            return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.displaySizeInches " + "asc" + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                    limit, offset);
        }
        return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.displaySizeInches " + "desc" + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                limit, offset);
    }

    private List<PhoneModel> sortingOnBrand(String order, int limit, int offset) {
        if (order.equals("asc")) {
            return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.brand " + "asc" + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                    limit, offset);
        }
        return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.brand " + "desc" + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                limit, offset);
    }

    private List<PhoneModel> sortingOnModel(String order, int limit, int offset) {
        if (order.equals("asc")) {
            return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.model " + "asc" + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                    limit, offset);
        }
        return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.model " + "desc" + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                limit, offset);
    }

    private List<PhoneModel> sortingOnPrice(String order, int limit, int offset) {
        if (order.equals("asc")) {
            return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.price " + "asc" + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                    limit, offset);
        }
        return resultsOfSorting(PHONES_SORTED_BY_FIELD_AND_ORDER_FIRST_PART + "phones.price " + "desc" + PHONES_SORTED_BY_FIELD_AND_ORDER_SECOND_PART,
                limit, offset);
    }

    public List<PhoneModel> findByAllParameters(ProductListPageParametersModel parametersModel) {
        String order = parametersModel.getOrderToSort();
        if (order.equals("desc")) {
            return findByAllWordsInQuery(parametersModel, SEARCHING_PHONES_BY_ALL_PARAMS_NAME_DESC_FIRST_PART + " " + "phones.".concat(parametersModel.getFieldToSort()) +
                    " " + order + SEARCHING_PHONES_BY_ALL_PARAMS_NAME_DESC_SECOND_PART);
        } else {
            return findByAllWordsInQuery( parametersModel,SEARCHING_PHONES_BY_ALL_PARAMS_NAME_DESC_FIRST_PART + " " + "phones.".concat(parametersModel.getFieldToSort()) +
                    " " + order + SEARCHING_PHONES_BY_ALL_PARAMS_NAME_DESC_SECOND_PART);
        }
    }

    public List<PhoneModel> findByAllWordsInQuery(ProductListPageParametersModel parametersModel, String queryForDataBase) {
        List<String> queryItems = Arrays.asList(parametersModel.getPhoneNameQuery().split(" "));
        List<PhoneModel> phoneModels = new ArrayList<>();
        for (String query : queryItems){
            phoneModels.addAll(jdbcTempl.query(queryForDataBase, new Object[]{
                    "%"+query.toLowerCase()+"%", "%"+query.toLowerCase()+"%",
                    parametersModel.getLimit(), parametersModel.getOffset()}, new PhoneExtractor()));
        }
        return phoneModels;
    }
}