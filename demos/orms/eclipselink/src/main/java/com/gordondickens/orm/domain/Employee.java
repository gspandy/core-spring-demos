package com.gordondickens.orm.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
public class Employee extends AbstractPersistable<Long> {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    private String comments;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

//	@Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private Long id;
//
//	@Version
//    @Column(name = "version")
//    private Integer version;
//
//	public Long getId() {
//        return this.id;
//    }
//
//	public void setId(Long id) {
//        this.id = id;
//    }
//
//	public Integer getVersion() {
//        return this.version;
//    }
//
//	public void setVersion(Integer version) {
//        this.version = version;
//    }

	public String getFirstName() {
        return this.firstName;
    }

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	public String getLastName() {
        return this.lastName;
    }

	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getComments() {
        return this.comments;
    }

	public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
