package com.gordondickens.jerseyrest.service;


import com.gordondickens.jerseyrest.domain.Item;
import com.gordondickens.jerseyrest.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Service
@Transactional
@Path("/rest")
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);


    @Autowired
    ItemRepository itemRepository;

//    @Context
//    UriInfo uriInfo;
//    @Context
//    Request request;

    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public String getAllItems() {
        logger.debug("*********** GET for All Items **********");

        List<Item> itemList = findAllItems();
        String result = "NO Items Found";
        if (itemList != null) {
            result = "Available Items: " + itemList;
        }
        logger.debug("*********** GET for All Items - returning '{}' **********", result);
        return result;
    }

    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    @Override
    public String getItem(@PathParam("id") Long id) {
        Item item = findItem(id);
        return (item != null ? item.toString() : "No Item Found for Item ID: "
                + id);
    }

    //    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public String saveRest(@Context Item item) {
        String result = null;
        if (item != null) {
            saveItem(item);
            result = "Item: " + item.toString() + " Saved.";
        } else
            result = "Item can not be null.";
        return result;
    }

    //    @PUT
//    @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_FORM_URLENCODED})
//    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public String update(@Context Item item) {
        String result = null;
        if (item != null) {
            update(item);
            result = "Item: " + item.toString() + " Updated.";
        } else
            result = "Item can not be null.";
        return result;
    }

    @Override
//    @DELETE
//    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    @Path("/{id}")
    public String deleteItem(@PathParam("id") Long id) {
        String result = null;
        if (id != null) {
            Item item = findItem(id);
            if (item != null) {
                deleteItem(item);
                result = "Item id " + id + " deleted.";
            } else
                result = "Item not found";
        } else
            result = "Item id can not be null";

        return result;
    }

    @Override
    public Item findItem(Long id) {
        return itemRepository.findOne(id);
    }

    @Override
    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public long countAllItems() {
        return itemRepository.count();
    }

    @Override
    public List<Item> findItemEntries(int firstResult, int maxResults) {
        return itemRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

    @Override
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public void saveItem(Item item) {
        logger.debug("*********** Preparing to save '{}'", item);
        itemRepository.save(item);
    }
}
