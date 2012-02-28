package com.gordondickens.jerseyrest;


import com.gordondickens.jerseyrest.domain.Item;
import com.gordondickens.jerseyrest.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ItemServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceTests.class);

    private static final String BASE_URI = "http://localhost:8080/ws/rest";

    RestTemplate restTemplate;

    @Autowired
    ItemService itemService;

    @Before
    @Rollback(false)
    public void beforeEachTest() {
        restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
        list.add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(list);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        headers.setAccept(mediaTypes);

        Item item = new Item();
        item.setDescription("Super Duper Sample Item");
        item.setName("The Test Item");


        HttpEntity<Item> httpEntity = new HttpEntity<Item>(item, headers);


    }

//    @Ignore
//    @Test
//    public void testRestClientPost() {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.TEXT_PLAIN);
//        List<MediaType> mediaTypes = new ArrayList<MediaType>();
//        mediaTypes.add(MediaType.TEXT_PLAIN);
//        headers.setAccept(mediaTypes);
//
//        Item item = new Item();
//        item.setDescription("Super Duper Sample Item");
//        item.setName("The Test Item");
//
//
//        HttpEntity<Item> httpEntity = new HttpEntity<Item>(item, headers);
//
//        ResponseEntity<Item> response = restTemplate.postForEntity(
//                BASE_URI, httpEntity, Item.class);
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        assertNotNull(response.getBody());
//        logger.debug("********* HERE IS YOUR RESPONSE BODY: {}", response.getBody());
//    }

    @Test
    public void testRestClientGet() {
        Item item = createItem();
        logger.debug("******** TESTING ********");

        ResponseEntity<String> response = restTemplate.getForEntity(
                BASE_URI + "/" + item.getId(), String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        logger.debug("********** Job Request Response: {}", response.getBody());
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected Item createItem() {

        Item item = new Item();
        item.setDescription("Test Description 1");
        item.setName("Test Name 1");

        itemService.saveItem(item);
        logger.debug("******** SET UP TEST RECORD: {} ********", item);
        return item;
    }

}
