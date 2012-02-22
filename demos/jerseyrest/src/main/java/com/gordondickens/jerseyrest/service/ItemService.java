package com.gordondickens.jerseyrest.service;

import com.gordondickens.jerseyrest.domain.Item;

import java.util.List;

public interface ItemService {

    public String getAllItems();

    public String getItem(Long id);

    public String deleteItem(Long id);

    public String saveRest(Item item);

    public long countAllItems();

    public void deleteItem(Item item);

    public Item findItem(Long id);

    public List<Item> findAllItems();

    public List<Item> findItemEntries(int firstResult, int maxResults);

    public void saveItem(Item item);

    public String update(Item item);

}
