package com.timcast.flurbovurbo.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="flurbos")
public class Flurbo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Integer id;

	@Getter @Setter
	private String description;
	
	@Getter @Setter
	private Date createDate;
	
	@Getter @Setter
	private Date modifiedDate;
	
	@Getter @Setter
	private boolean activeIndicator;
	
}
