package com.es.core.service;

import com.es.core.model.PhoneModel;
import com.es.core.model.ProductListPageParametersModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Service
public class PagingService {

    @Resource
    private PhoneService phoneService;

    private int PHONES_ON_PAGE = 10;

    private double COUNT_PAGES;

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String COUNT_PAGES_IN_SEARCHING_BY_QUERY = "select count(DISTINCT tableB.id) from (select tempPhone.id " +
            "from (select * from phones join stocks on stocks.phoneId=id where stocks.stock>0 " +
            "and lower(phones.model) like ? or lower(phones.description) like ?  )" +
            "as tempPhone left join phone2color as p2c on tempPhone.id=p2c.phoneId " +
            "left join colors on p2c.colorId=colors.id) as tableB";

    public List<PhoneModel> listPages(ProductListPageParametersModel parametersModel, Model model, HttpSession session) {
        Integer page = parametersModel.getPage();
        List<PhoneModel> phoneList;
        COUNT_PAGES = countPages(parametersModel, session);
        if (page > 0 && page < COUNT_PAGES) {
            session.setAttribute("page", page);
        }
        if (page <= 0) {
            page = 1;
            session.setAttribute("page", page);
        }
        if (page >= COUNT_PAGES) {
            session.setAttribute("page", (COUNT_PAGES - 1));
        }
        phoneList = phoneService.searchFor(parametersModel);
        model.addAttribute("phones", phoneList);
        return phoneList;
    }

    private int countPages(ProductListPageParametersModel parametersModel, HttpSession session) {
        int limit = PHONES_ON_PAGE;
        int offset = PHONES_ON_PAGE * (parametersModel.getPage() - 1);
        parametersModel.setLimit(limit);
        parametersModel.setOffset(offset);
        List<String> queryItems = Arrays.asList(parametersModel.getPhoneNameQuery().split(" "));
        double counter = 0;
        for (String query : queryItems) {
            counter += jdbcTemplate.queryForObject(COUNT_PAGES_IN_SEARCHING_BY_QUERY, Double.class, new Object[]{
                    "%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%"});
        }
        COUNT_PAGES = (int) Math.ceil(counter / PHONES_ON_PAGE);
        session.setAttribute("maxPages", COUNT_PAGES);
        return (int) COUNT_PAGES;
    }
}
