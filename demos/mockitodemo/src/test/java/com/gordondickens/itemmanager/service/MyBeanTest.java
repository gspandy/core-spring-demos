package com.gordondickens.itemmanager.service;

import com.gordondickens.itemmanager.entity.MyBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest(IdGenerator.class)
//@PowerMockIgnore({"org.w3c.*", "javax.xml.*"})
public class MyBeanTest {
    private static final Logger logger = LoggerFactory.getLogger(MyBeanTest.class);

    private MyBean myBean;

    @Before
    public void setUp() {
        logger.debug("PowerMock test with Mockito on Final class with Static Method");
        myBean = new MyBean();
    }

    @Test
    public void mockStaticMethod() throws Exception {
        // Given
        final long expectedId = 2L;
        mockStatic(IdGenerator.class);
        when(IdGenerator.generateNewId()).thenReturn(expectedId);

        // When
        final long value = myBean.createData();

        // Then
        assertEquals(expectedId, value);
    }

    @Test
    public void mockStaticMethodAndVerify() throws Exception {
        // Given
        final long expectedId = 2L;
        mockStatic(IdGenerator.class);
        when(IdGenerator.generateNewId()).thenReturn(expectedId);

        // When
        final long value = myBean.createData();

        // Then
        assertEquals(expectedId, value);
        verifyStatic();
        IdGenerator.generateNewId();
    }

    @Test
    public void stubStaticMethod() throws Exception {
        // Given
        final long expectedId = 2L;
        stub(method(IdGenerator.class, "generateNewId")).toReturn(expectedId);

        // When
        final long value = myBean.createData();

        // Then
        assertEquals(expectedId, value);
    }

    @Test
    public void suppressStaticMethod() throws Exception {
        // Given
        suppress(method(IdGenerator.class, "generateNewId"));

        // When
        final long value = myBean.createData();

        // Then
        assertEquals(0L, value);
    }
}