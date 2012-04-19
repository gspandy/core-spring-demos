package com.gordondickens.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: gordondickens
 * Date: 4/18/12
 * Time: 10:41 PM
 */

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class DuplicateBeanTests {
    private static final Logger logger = LoggerFactory.getLogger(DuplicateBeanTests.class);

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testMe() {
        logger.debug("Test Run");
        logger.debug("SimpleBean '{}'", applicationContext.getBean("simpleBean").toString());
    }

}
