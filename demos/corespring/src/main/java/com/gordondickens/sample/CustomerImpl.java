package com.gordondickens.sample;

import com.gordondickens.propeditor.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

//@Component("customer")
public class CustomerImpl implements Customer, InitializingBean {
    private static final Logger logger = LoggerFactory
            .getLogger(CustomerImpl.class);

    String firstName = null;
    String lastName = null;
    PhoneNumber phoneNumber = null;

    public CustomerImpl() {
        logger.debug("CUSTIMPL constructed = '{}'", this.toString());
    }

    public CustomerImpl(String lastName) {
        this.lastName = lastName;
        logger.debug("CUSTIMPL lastname constructed = '{}'", this.toString());
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    // @Required
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    @Override
//    public PhoneNumber getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    @Override
//    public void setPhoneNumber(PhoneNumber pn) {
//        this.phoneNumber = pn;
//    }

    @PostConstruct
    public void runThis() {
        logger.debug("**** Post Construct Annotation");
    }

    public void init() {
        logger.debug("**** Init-Method Attribute");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("**** InitializingBean");

    }

    @Override
    public String toString() {
        return "CustomerImpl [firstName=" + firstName + ", lastName="
                + lastName + ", phoneNumber=" + phoneNumber + "]";
    }


}
