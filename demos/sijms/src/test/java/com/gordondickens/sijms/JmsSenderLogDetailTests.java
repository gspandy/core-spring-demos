package com.gordondickens.sijms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: gordon Date: 4/16/11 Time: 12:46 PM
 */
@ContextConfiguration("classpath:/com/gordondickens/sijms/JmsSenderTests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JmsSenderLogDetailTests {

    private static final Logger logger = LoggerFactory
            .getLogger(JmsSenderLogDetailTests.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MyJmsGateway myJmsGateway;

    @Before
    public void setup() {
        logger.debug("********************");
        String[] beans = applicationContext.getBeanDefinitionNames();
        for (String o : beans) {
            logger.debug("________________________");
            logger.debug("BEAN = " + o);
            logger.debug("\tType = " + applicationContext.getType(o));
            String[] aliases = applicationContext.getAliases(o);
            if (aliases != null && aliases.length > 0) {
                for (String a : aliases) {
                    logger.debug("\tAliased as: " + a);
                }
            }
        }

        logger.debug("********************");
        logger.debug("*** Number of Beans = {} ***",
                applicationContext.getBeanDefinitionCount());
        logger.debug("********************");
    }

    @Test
    public void testJmsSend() {
        myJmsGateway.sendMyMessage("myHeaderValue", "MY PayLoad");
        // Assert.assertNotNull(jmsTemplate.receiveAndConvert("my.inbound.queue"));
    }
}
