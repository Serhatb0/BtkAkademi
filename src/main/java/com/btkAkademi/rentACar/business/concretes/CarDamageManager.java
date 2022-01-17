package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarDamageService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarDamageListDto;
import com.btkAkademi.rentACar.business.requests.carDamageRequest.CreateCarDamageRequest;
import com.btkAkademi.rentACar.business.requests.carDamageRequest.UpdateCarDamageRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CarDamageDao;
import com.btkAkademi.rentACar.entities.concretes.CarDamage;

@Service
public class CarDamageManager implements CarDamageService {

	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService) {
		super();
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {
		CarDamage carDamage = modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);

		this.carDamageDao.save(carDamage);
		return new SuccessResult(Messages.carDamageAdded);
	}

	public CarDamageManager(CarDamageDao carDamageDao) {
		super();
		this.carDamageDao = carDamageDao;
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAll() {
		List<CarDamage> carDamageList = this.carDamageDao.findAll();
		List<CarDamageListDto> response = carDamageList.stream()
				.map(carDamage -> modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarDamageListDto>>(response);

	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {
		if (this.carDamageDao.existsById(updateCarDamageRequest.getId())) {
			CarDamage carDamage = this.carDamageDao.findById(updateCarDamageRequest.getId());

			carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
			this.carDamageDao.save(carDamage);
			return new SuccessResult(Messages.carDamageUpdated);
		}
		return new ErrorResult(Messages.carDamageIsNotFound);

	}

	@Override
	public Result deleteById(int id) {
		if (this.carDamageDao.existsById(id)) {
			this.carDamageDao.deleteById(id);
			return new SuccessResult(Messages.carDamageDeleted);
		}
		return new ErrorResult(Messages.carDamageIsNotFound);
	}

}
