package com.btkAkademi.rentACar.business.requests.customerRequest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomer {

	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private LocalDate birthDate;

}
