package com.gordondickens.sample;

import com.gordondickens.MyBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: gordondickens
 * Date: 1/24/12
 * Time: 3:30 PM
 */
@ContextConfiguration()
@RunWith(SpringJUnit4ClassRunner.class)
public class MyBeanTest {
    private static final Logger logger = LoggerFactory.getLogger(MyBeanTest.class);

    @Autowired
    ApplicationContext applicationContext;


    @Test
    @Repeat(5)
    public void doThis() throws Exception {
        logger.debug("Waiting...");
        Thread.sleep(2000);
        logger.debug("Getting...");
        MyBean myBean = applicationContext.getBean(MyBean.class);

    }


}
