package com.btkAkademi.rentACar.entities.concretes;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "promosyon_code")
public class PromosyonCode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "promosyon_code")
	private String promosyonCode;
	@Column(name = "discount_rate")
	private int discountRate;
	@Column(name = "promosyon_start")
	private LocalDate promosyonStart;
	@Column(name = "promosyon_end")
	private LocalDate promosyonEnd;
	
	
}