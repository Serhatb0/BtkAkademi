package com.btkAkademi.rentACar.business.requests.carSegmentReuqest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarSegmentRequest {
	private String segmentName;
}
