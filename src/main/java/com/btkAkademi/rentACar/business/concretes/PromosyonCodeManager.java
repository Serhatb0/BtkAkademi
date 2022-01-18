package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.btkAkademi.rentACar.business.abstracts.PromosyonCodeService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.PromosyonCodeListDto;
import com.btkAkademi.rentACar.business.requests.promosyonRequest.CreatePromosyonCodeRequest;
import com.btkAkademi.rentACar.business.requests.promosyonRequest.UpdatePromosyonCodeRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.PromosyonCodeDao;
import com.btkAkademi.rentACar.entities.concretes.PromosyonCode;

@Service
public class PromosyonCodeManager implements PromosyonCodeService {

	private PromosyonCodeDao promosyonDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public PromosyonCodeManager(PromosyonCodeDao promosyonDao, ModelMapperService modelMapperService) {
		super();
		this.promosyonDao = promosyonDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<PromosyonCode> findById(int id) {
		return new SuccessDataResult<PromosyonCode>(this.promosyonDao.findById(id));
	}

	@Override
	public boolean exsistById(int id) {
		return this.promosyonDao.existsById(id);
	}

	@Override
	public DataResult<List<PromosyonCodeListDto>> getAll() {
		List<PromosyonCode> promosyonList = this.promosyonDao.findAll();
		List<PromosyonCodeListDto> response = promosyonList.stream()
				.map(promosyon -> modelMapperService.forDto().map(promosyon, PromosyonCodeListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<PromosyonCodeListDto>>(response);
	}

	@Override
	public Result add(CreatePromosyonCodeRequest createPromosyonRequest) {
		PromosyonCode promosyonCode = modelMapperService.forRequest().map(createPromosyonRequest, PromosyonCode.class);

		this.promosyonDao.save(promosyonCode);
		return new SuccessResult(Messages.promosyonAdded);
	}

	@Override
	public boolean promosyonCodeTime(LocalDate promosyonStart, LocalDate promosyonEnd) {
		int promosyonCodeTime = LocalDate.now().getDayOfMonth() -promosyonEnd.getDayOfMonth()  ;
		if (promosyonCodeTime <= 0) {
			return true;
		}
		return false;

	}

	@Override
	public Result update(UpdatePromosyonCodeRequest updatePromosyonRequest) {
		if(this.promosyonDao.existsById(updatePromosyonRequest.getId())) {
			PromosyonCode promosyonCode = this.promosyonDao.findById(updatePromosyonRequest.getId());
					promosyonCode = modelMapperService.forRequest().map(updatePromosyonRequest, PromosyonCode.class);
			this.promosyonDao.save(promosyonCode);
			return new SuccessResult(Messages.brandUpdated);
		}
		return new ErrorResult(Messages.promosyonIsNotFound);
	}

	@Override
	public Result deleteById(int id) {
		if(this.promosyonDao.existsById(id)) {
			this.promosyonDao.deleteById(id);
			return new SuccessResult(Messages.promosyonDeleted);
		}
		
		return new ErrorResult(Messages.promosyonIsNotFound);
	}

}
