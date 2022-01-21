package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.btkAkademi.rentACar.business.abstracts.CreditCardService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CreditCardListDto;
import com.btkAkademi.rentACar.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.btkAkademi.rentACar.business.requests.creditCardRequest.UpdateCreditCardRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CreditCardDao;
import com.btkAkademi.rentACar.entities.concretes.CreditCard;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CreditCardManager(CreditCardDao accountDao, ModelMapperService modelMapperService) {
		super();
		this.creditCardDao = accountDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<CreditCard> findById(int id) {
		return new SuccessDataResult<CreditCard>(this.creditCardDao.findById(id));

	}

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {

		CreditCard creditCard = modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);

		this.creditCardDao.save(creditCard);
		return new SuccessResult(Messages.creditCardAdded);
	}

	@Override
	public DataResult<List<CreditCardListDto>> getAll() {
		List<CreditCard> creditCardList = this.creditCardDao.findAll();
		List<CreditCardListDto> response = creditCardList.stream()
				.map(creditCard -> modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CreditCardListDto>>(response);
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) {

		if (this.creditCardDao.existsById(updateCreditCardRequest.getId())) {
			CreditCard account = this.creditCardDao.findById(updateCreditCardRequest.getId());

			account = this.modelMapperService.forRequest().map(updateCreditCardRequest, CreditCard.class);
			this.creditCardDao.save(account);
			return new SuccessResult(Messages.creditCardUpdated);
		}
		return new ErrorResult(Messages.creditCardIsNotFound);

	}

	@Override
	public Result deleteById(int id) {
		if (this.creditCardDao.existsById(id)) {
			this.creditCardDao.deleteById(id);
			return new SuccessResult(Messages.creditCardDeleted);
		}
		return new ErrorResult(Messages.creditCardIsNotFound);
	}

}
