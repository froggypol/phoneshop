package com.es.core.form;

import com.es.core.util.validators.order.form.validation.ValidOrderForm;

@ValidOrderForm
public class OrderForm {

    private String firstName;

    private String lastName;

    private String deliveryAddress;

    private String contactPhoneNo;

    private String otherInfo;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getContactPhoneNo() {
        return contactPhoneNo;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setContactPhoneNo(String contactPhoneNo) {
        this.contactPhoneNo = contactPhoneNo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
