package com.btkAkademi.rentACar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "credit_card")
	String creditCard;
	@Column(name = "valid_date")
	String validDate;
	@Column(name = "cvc")
	String cVC;
	@Column(name = "sms_password")
	String smsPassword;
//	@Column(name = "user_id")
//	private int userId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	

}
