package com.gordondickens.simail.domain;

import com.gordondickens.simail.repository.RecipientRepository;
import com.gordondickens.simail.service.RecipientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
public class RecipientIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    private RecipientDataOnDemand dod;

    @Autowired
    RecipientService itemService;

    @Autowired
    RecipientRepository recipientRepository;

    @Test
    public void testCountAllRecipients() {
        Assert.assertNotNull("Data on demand for 'Recipient' failed to initialize correctly", dod.getRandomRecipient());
        long count = itemService.countAllRecipients();
        Assert.assertTrue("Counter for 'Recipient' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindRecipient() {
        Recipient obj = dod.getRandomRecipient();
        Assert.assertNotNull("Data on demand for 'Recipient' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Recipient' failed to provide an identifier", id);
        obj = itemService.findRecipient(id);
        Assert.assertNotNull("Find method for 'Recipient' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Recipient' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllRecipients() {
        Assert.assertNotNull("Data on demand for 'Recipient' failed to initialize correctly", dod.getRandomRecipient());
        long count = itemService.countAllRecipients();
        Assert.assertTrue("Too expensive to perform a find all test for 'Recipient', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Recipient> result = itemService.findAllRecipients();
        Assert.assertNotNull("Find all method for 'Recipient' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Recipient' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindRecipientEntries() {
        Assert.assertNotNull("Data on demand for 'Recipient' failed to initialize correctly", dod.getRandomRecipient());
        long count = itemService.countAllRecipients();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Recipient> result = itemService.findRecipientEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Recipient' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Recipient' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testFlush() {
        Recipient obj = dod.getRandomRecipient();
        Assert.assertNotNull("Data on demand for 'Recipient' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Recipient' failed to provide an identifier", id);
        obj = itemService.findRecipient(id);
        Assert.assertNotNull("Find method for 'Recipient' illegally returned null for id '" + id + "'", obj);
        boolean modified = dod.modifyRecipient(obj);
        Integer currentVersion = obj.getVersion();
        recipientRepository.flush();
        Assert.assertTrue("Version for 'Recipient' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

    @Test
    public void testUpdateRecipientUpdate() {
        Recipient obj = dod.getRandomRecipient();
        Assert.assertNotNull("Data on demand for 'Recipient' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Recipient' failed to provide an identifier", id);
        obj = itemService.findRecipient(id);
        boolean modified = dod.modifyRecipient(obj);
        Integer currentVersion = obj.getVersion();
        Recipient merged = itemService.updateRecipient(obj);
        recipientRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Recipient' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

    @Test
    public void testSaveRecipient() {
        Assert.assertNotNull("Data on demand for 'Recipient' failed to initialize correctly", dod.getRandomRecipient());
        Recipient obj = dod.getNewTransientRecipient(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Recipient' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Recipient' identifier to be null", obj.getId());
        itemService.saveRecipient(obj);
        recipientRepository.flush();
        Assert.assertNotNull("Expected 'Recipient' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testDeleteRecipient() {
        Recipient obj = dod.getRandomRecipient();
        Assert.assertNotNull("Data on demand for 'Recipient' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Recipient' failed to provide an identifier", id);
        obj = itemService.findRecipient(id);
        itemService.deleteRecipient(obj);
        recipientRepository.flush();
        Assert.assertNull("Failed to remove 'Recipient' with identifier '" + id + "'", itemService.findRecipient(id));
    }
}
