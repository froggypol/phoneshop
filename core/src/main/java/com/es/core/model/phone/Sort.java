package com.es.core.model.phone;

import java.util.List;
import java.util.stream.Collectors;

public class Sort {

    private List<Phone> listToSort;

    public Sort(List<Phone> listToSort) {
        this.listToSort = listToSort;
    }

    public List<Phone> sortProductsList(String fieldToSort, String orderToSort) {
        return fieldToSort.equals("size") ?
                           sortingOnSize(orderToSort) :
               fieldToSort.equals("brand") ?
                           sortingOnBrand(orderToSort) :
               fieldToSort.equals("model") ?
                           sortingOnModel(orderToSort) :
               sortingOnPrice(orderToSort);
    }

    private List<Phone> sortingOnSize(String order) {
        return listToSort.stream().sorted((prod1, prod2) -> {
            return order.equals("asc") ?
                    prod1.getDisplaySizeInches().compareTo(prod2.getDisplaySizeInches()) :
                    prod2.getDisplaySizeInches().compareTo(prod1.getDisplaySizeInches());
        })   .collect(Collectors.toList());
    }

    private List<Phone> sortingOnBrand(String order) {
        return listToSort.stream().sorted((prod1, prod2) -> {
            return order.equals("asc") ?
                    prod1.getBrand().compareTo(prod2.getBrand()) :
                    prod2.getBrand().compareTo(prod1.getBrand());
        })              .collect(Collectors.toList());
    }

    private List<Phone> sortingOnModel(String order) {
        return listToSort.stream().sorted((prod1, prod2) -> {
            return order.equals("asc") ?
                    prod1.getModel().compareTo(prod2.getModel()) :
                    prod2.getModel().compareTo(prod1.getModel());
        })
                .collect(Collectors.toList());
    }

    private List<Phone> sortingOnPrice(String order) {
                     return listToSort.stream().filter(phone -> phone.getPrice() != null)
                                      .sorted((prod1, prod2) -> {
                                          return order.equals("asc") ?
                                                  prod1.getPrice().compareTo(prod2.getPrice()) :
                                                  prod2.getPrice().compareTo(prod1.getPrice());
         })                           .collect(Collectors.toList());
    }

    public List<Phone> getListToSort() {
        return listToSort;
    }

    public void setListToSort(List<Phone> listToSort) {
        this.listToSort = listToSort;
    }
}
