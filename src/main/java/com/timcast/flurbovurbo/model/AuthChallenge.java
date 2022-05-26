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
@Table(name="challenges")
public class AuthChallenge implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Integer id;
	@Getter @Setter
	private String question;
	@Getter @Setter
	private String possibleAnswer1;
	@Getter @Setter
	private String possibleAnswer2;
	@Getter @Setter
	private String possibleAnswer3;
	@Getter @Setter
	private String possibleAnswer4;
	@Getter @Setter
	private String correctAnswer;
	@Getter @Setter
	private Date createDate;
	@Getter @Setter
	private Date modifiedDate;
	@Getter @Setter
	private boolean activeIndicator;
	
}
