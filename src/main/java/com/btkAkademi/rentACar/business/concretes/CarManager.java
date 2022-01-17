package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarListDto;

import com.btkAkademi.rentACar.business.requests.carRequest.CreateCarRequest;
import com.btkAkademi.rentACar.business.requests.carRequest.UpdateCarRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CarDao;

import com.btkAkademi.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {
	private CarDao carDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CarListDto>> getAll() {
		List<Car> carList = this.carDao.findAll();
		List<CarListDto> response = carList.stream().map(car -> modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		Car car = modelMapperService.forRequest().map(createCarRequest, Car.class);

		this.carDao.save(car);
		return new SuccessResult(Messages.carAdded);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		Car car = this.carDao.findById(updateCarRequest.getId());
		if (car == null) {
			return new ErrorResult(Messages.carIsNotFound);
		}

		car = modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carDao.save(car);
		return new SuccessResult(Messages.carUpdated);

	}

	@Override
	public DataResult<List<CarListDto>> getAllRentalPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		Page<Car> rentalList = this.carDao.findByRentals_IdIsNullOrRentals_returnDateIsNotNull(pageable);
		List<CarListDto> response = rentalList.stream()
				.map(car -> modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public DataResult<Car> findByRentals_Id(int id) {
		return new SuccessDataResult<Car>(this.carDao.findByRentals_Id(id));
	}

	@Override
	public DataResult<Car> findById(int id) {
		return new SuccessDataResult<Car>(this.carDao.findById(id));
	}

}
