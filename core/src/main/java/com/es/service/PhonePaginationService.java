package com.es.service;

import com.es.core.dao.JdbcPhonePaginationDao;
import com.es.core.model.PhonePaginationModel;
import com.es.core.model.PhoneModel;
import com.es.core.model.ProductListPageParametersModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class PhonePaginationService {

    @Resource
    private HttpSession httpSession;

    @Resource
    private PhoneService phoneService;

    @Resource
    private JdbcPhonePaginationDao paginationDao;

    public List<PhoneModel> listPages(ProductListPageParametersModel parametersModel) {
        Integer page = parametersModel.getPage();
        List<PhoneModel> phoneList;
        PhonePaginationModel paginationModel = new PhonePaginationModel();
        int pageNumber = countPages(parametersModel, paginationModel);
        paginationModel.setCountPages(pageNumber);
        if (page > 0 && page < pageNumber) {
            httpSession.setAttribute("page", page);
        }
        if (page <= 0) {
            page = 1;
            httpSession.setAttribute("page", page);
        }
        if (page >= pageNumber) {
            httpSession.setAttribute("page", (pageNumber - 1));
        }
        phoneList = phoneService.searchFor(parametersModel);
        return phoneList;
    }

    private int countPages(ProductListPageParametersModel parametersModel, PhonePaginationModel paginationModel) {
        double counter;
        int limit = paginationModel.getPhonesOnPage();
        int offset = paginationModel.getPhonesOnPage() * (parametersModel.getPage() - 1);
        parametersModel.setLimit(limit);
        parametersModel.setOffset(offset);
        counter = paginationDao.countPages(parametersModel.getPhoneNameQuery());
        paginationModel.setCountPages((int) Math.ceil(counter / paginationModel.getPhonesOnPage()));
        httpSession.setAttribute("maxPages", paginationModel.getCountPages());
        return paginationModel.getPhonesOnPage();
    }
}
