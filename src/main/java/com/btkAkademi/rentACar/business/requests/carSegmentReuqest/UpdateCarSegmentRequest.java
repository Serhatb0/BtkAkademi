package com.btkAkademi.rentACar.business.requests.carSegmentReuqest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarSegmentRequest {
	
	private int id;
	private String segmentName;

}
