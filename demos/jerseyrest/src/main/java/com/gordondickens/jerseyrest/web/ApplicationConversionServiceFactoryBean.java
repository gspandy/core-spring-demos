package com.gordondickens.jerseyrest.web;

import com.gordondickens.jerseyrest.domain.Item;
import com.gordondickens.jerseyrest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

/**
 * A central place to register application converters and formatters. 
 */
@Configurable
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    @Autowired
    ItemService itemService;

    @Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

    public Converter<Item, String> getItemToStringConverter() {
        return new Converter<Item, String>() {
            public String convert(Item item) {
                return new StringBuilder().append(item.getName()).append(" ").append(item.getDescription()).toString();
            }
        };
    }

    public Converter<String, Item> getStringToItemConverter() {
        return new Converter<String, Item>() {
            public Item convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Item.class);
            }
        };
    }

    public Converter<Long, Item> getIdToItemConverter() {
        return new Converter<Long, Item>() {
            public Item convert(Long id) {
                return itemService.findItem(id);
            }
        };
    }

    public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getItemToStringConverter());
        registry.addConverter(getIdToItemConverter());
        registry.addConverter(getStringToItemConverter());
    }

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
