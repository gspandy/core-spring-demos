package com.gordondickens.simail.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Recipient {

    private String recipientEmail;

    private String subject;

    private String messageBody;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

    public static Recipient fromJsonToRecipient(String json) {
        return new JSONDeserializer<Recipient>().use(null, Recipient.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Recipient> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

    public static Collection<Recipient> fromJsonArrayToRecipients(String json) {
        return new JSONDeserializer<List<Recipient>>().use(null, ArrayList.class).use("values", Recipient.class).deserialize(json);
    }

    public String getRecipientEmail() {
        return this.recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
