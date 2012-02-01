package com.gordondickens;

/**
 * User: gordondickens
 * Date: 1/25/12
 * Time: 4:40 PM
 */
public class MyDepBean {
    String comment;
    
    MyDepBean(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "MyDepBean{" +
                "comment='" + comment + '\'' +
                '}';
    }
}
