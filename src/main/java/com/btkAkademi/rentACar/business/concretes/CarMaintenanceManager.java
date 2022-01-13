package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarMaintenanceService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarMaintenanceListDto;
import com.btkAkademi.rentACar.business.requests.carMaintenanceRequest.CreateCarMaintenanceRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CarMaintenanceDao;

import com.btkAkademi.rentACar.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService) {
		super();
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() {

		List<CarMaintenance> carMaintenanceList = this.carMaintenanceDao.findAll();
		List<CarMaintenanceListDto> response = carMaintenanceList.stream()
				.map(brand -> modelMapperService.forDto().map(brand, CarMaintenanceListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarMaintenanceListDto>>(response);

	}

	@Override
	public Result add(CreateCarMaintenanceRequest carMaintenanceRequest) {
		CarMaintenance carMaintenance = modelMapperService.forRequest().map(carMaintenanceRequest,
				CarMaintenance.class);

		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessResult(Messages.carMaintenanceAdded);
	}

	@Override
	public DataResult<CarMaintenance> findByIdAndDateOfArrivalIsNotNull(int id) {
	
		return new SuccessDataResult<CarMaintenance>(this.carMaintenanceDao.findByIdAndDateOfArrivalIsNotNull(id));
	}

}
