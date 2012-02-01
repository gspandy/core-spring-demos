package com.gordondickens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * User: gordondickens
 * Date: 1/24/12
 * Time: 3:29 PM
 */
public class MyBean {
    private static final Logger logger = LoggerFactory.getLogger(MyBean.class);
    private String myData;
    private String myDataToo;

    // Can autowire without setter
    @Autowired
    private MyDepBean myDepBean;

    public String getMyData() {
        return myData;
    }

    public void setMyData(String myData) {
        this.myData = myData;
    }

    public String getMyDataToo() {
        return myDataToo;
    }

    public void setMyDataToo(String myDataToo) {
        this.myDataToo = myDataToo;
    }

    @PreDestroy
    public void postProcessMeToo() {
        logger.debug(" >>>>> PRE DESTROY - PRE DESTROY - PRE DESTROY - PRE DESTROY - PRE DESTROY >>>>>");
    }

    @PostConstruct
    public void postProcessMe() {
        if (myDepBean != null) {
            logger.debug("MY DEP BEAN {}", myDepBean.toString());
        }
        logger.debug("<<<<< POST CONSTRUCT - POST CONSTRUCT - POST CONSTRUCT - POST CONSTRUCT - POST CONSTRUCT <<<<<");
    }


}
