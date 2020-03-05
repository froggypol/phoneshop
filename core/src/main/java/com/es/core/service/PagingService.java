package com.es.core.service;

import com.es.core.model.PhoneModel;
import com.es.core.model.ProductListPageParametersModel;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private int COUNT_PAGES;

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static final String COUNT_PAGES_IN_SEARCHING_BY_QUERY = "select count(resultTable.id) from (select phoneWithPriceStock.id " +
            "from (select * from phones join stocks on stocks.phoneId=id )" +
            "as phoneWithPriceStock join phone2color as p2c on phoneWithPriceStock.id=p2c.phoneId " +
            "join colors on p2c.colorId=colors.id where ((lower(phoneWithPriceStock.model) like ? or lower(phoneWithPriceStock.description) like ?) " +
            "and phoneWithPriceStock.price is not null and phoneWithPriceStock.stock > 0 )) as resultTable";

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
        double counter = 0;
        int limit = PHONES_ON_PAGE;
        int offset = PHONES_ON_PAGE * (parametersModel.getPage() - 1);
        String query = parametersModel.getPhoneNameQuery();
        parametersModel.setLimit(limit);
        parametersModel.setOffset(offset);
        List<String> queryItems = Arrays.asList(query.split(" "));
        if (queryItems.size() == 0 ) {
            counter = jdbcTemplate.queryForObject(COUNT_PAGES_IN_SEARCHING_BY_QUERY, Double.class, new Object[]{"%%", "%%"});
        } else {
            for (String qr : queryItems) {
                counter += jdbcTemplate.queryForObject(COUNT_PAGES_IN_SEARCHING_BY_QUERY, Double.class, new Object[]{
                        "%" + qr.toLowerCase() + "%", "%" + qr.toLowerCase() + "%"});
            }
        }
        COUNT_PAGES = (int) Math.ceil(counter / PHONES_ON_PAGE);
        session.setAttribute("maxPages", COUNT_PAGES);
        return COUNT_PAGES;
    }
}
