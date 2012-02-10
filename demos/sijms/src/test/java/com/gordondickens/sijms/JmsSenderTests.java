package com.gordondickens.sijms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: gordon Date: 4/16/11 Time: 12:46 PM
 */
@ContextConfiguration("JmsSenderTests-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JmsSenderTests {

    @Autowired
    MyJmsGateway myJmsGateway;

    @Test
    public void testJmsSend() {
        myJmsGateway.sendMyMessage("myHeaderValue", "MY PayLoad");
        myJmsGateway.sendMyMessage("MY Other PayLoad");
    }
}
