package com.es.core.model.phone;


public class StockQueryForDatabase {

    public static String SELECT_NECESSARY_PHONE_WITH_STOCK = "select * from stocks where stocks.phoneId=?";

    public static String UPDATE_RESERVED_COUNTER_IN_STOCK = "update stocks set (reserved) = (?)";
}
