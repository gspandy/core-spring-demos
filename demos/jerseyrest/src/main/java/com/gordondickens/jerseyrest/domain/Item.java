package com.gordondickens.jerseyrest.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@XmlRootElement
public class Item {

    @Size(min = 3, max = 30)
    private String name;

    @Size(max = 255)
    private String description;
    @Version
    @Column(name = "version")
    private Integer version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static Item fromJsonToItem(String json) {
        return new JSONDeserializer<Item>().use(null, Item.class).deserialize(json);
    }

    public static Collection<Item> fromJsonArrayToItems(String json) {
        return new JSONDeserializer<List<Item>>().use(null, ArrayList.class).use("values", Item.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Item> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

    public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

}
