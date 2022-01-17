package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.PromosyonService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.BrandListDto;
import com.btkAkademi.rentACar.business.dtos.PromosyonListDto;
import com.btkAkademi.rentACar.business.requests.promosyonRequest.CreatePromosyonRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.PromosyonDao;
import com.btkAkademi.rentACar.entities.concretes.Brand;
import com.btkAkademi.rentACar.entities.concretes.Promosyon;

@Service
public class PromosyonManager implements PromosyonService {

	private PromosyonDao promosyonDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public PromosyonManager(PromosyonDao promosyonDao, ModelMapperService modelMapperService) {
		super();
		this.promosyonDao = promosyonDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<Promosyon> findById(int id) {
		return new SuccessDataResult<Promosyon>(this.promosyonDao.findById(id));
	}

	@Override
	public boolean exsistById(int id) {
		return this.promosyonDao.existsById(id);
	}

	@Override
	public DataResult<List<PromosyonListDto>> getAll() {
		List<Promosyon> promosyonList = this.promosyonDao.findAll();
		List<PromosyonListDto> response = promosyonList.stream()
				.map(promosyon -> modelMapperService.forDto().map(promosyon, PromosyonListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<PromosyonListDto>>(response);
	}

	@Override
	public Result add(CreatePromosyonRequest createPromosyonRequest) {
		Promosyon promosyon = modelMapperService.forRequest().map(createPromosyonRequest, Promosyon.class);

		this.promosyonDao.save(promosyon);
		return new SuccessResult(Messages.promosyonAdded);
	}

	@Override
	public boolean promosyonCodeTime(LocalDate promosyonStart, LocalDate promosyonEnd) {
		int promosyonCodeTime = promosyonStart.getDayOfMonth() - promosyonEnd.getDayOfMonth();
		if (promosyonCodeTime <= 0) {
			return true;
		}
		return false;

	}

}
