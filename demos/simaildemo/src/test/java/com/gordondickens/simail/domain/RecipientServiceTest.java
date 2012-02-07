package com.gordondickens.simail.domain;

import com.gordondickens.simail.integration.MailGateway;
import com.gordondickens.simail.repository.RecipientRepository;
import com.gordondickens.simail.service.RecipientService;
import com.gordondickens.simail.service.RecipientServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipientServiceTest {
    private RecipientService recipientService;
    private Recipient recipient;

    @Mock
    RecipientRepository recipientRepository;
    @Mock
    MailGateway mailGateway;

    @Before
    public void beforeEachTest() throws Exception {
        MockitoAnnotations.initMocks(recipientRepository);
        recipientService = new RecipientServiceImpl();
        recipientService.setMailGateway(mailGateway);
        recipientService.setRecipientRepository(recipientRepository);
        recipient = new Recipient();
        recipient.setId(1L);
        recipient.setMessageBody("Hello from Test");
        recipient.setRecipientEmail("gordon@gordondickens.com");
        recipient.setSubject("Hello Subject");
        recipient.setVersion(1);
    }

    @Test
    public void testSaveRecipient() {
        Assert.assertNotNull("Data on demand for 'Recipient' failed to provide a new transient entity", recipient);
        recipientService.saveRecipient(recipient);
        recipientRepository.flush();
        Assert.assertNotNull("Expected 'Recipient' identifier to no longer be null", recipient.getId());
        verify(recipientRepository, atLeastOnce()).save(recipient);
        verify(mailGateway, atLeastOnce()).sendMail(recipient);
    }
}
