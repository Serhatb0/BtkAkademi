package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.CarListDto;
import com.btkAkademi.rentACar.business.requests.carRequest.CreateCarRequest;
import com.btkAkademi.rentACar.business.requests.carRequest.UpdateCarRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Car;

public interface CarService {

	DataResult<List<CarListDto>> getAll();

	Result add(CreateCarRequest createCarRequest);

	Result update(UpdateCarRequest updateCarRequest);

	DataResult<List<CarListDto>> findSuitableCar(int pageNo, int pageSize);

	DataResult<Car> findByRentals_Id(int id);
	
	
	DataResult<Car> findById(int id);
	
	DataResult<List<CarListDto>> findByCarSegmentName(String segmentName);
		
	DataResult<CarListDto> findByCarListDtoId(int id);
	
	DataResult<List<Integer>> findAvailableCarBySegment(int segmentId);
	



}
