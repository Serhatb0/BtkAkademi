package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.btkAkademi.rentACar.business.abstracts.AccountService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.AccountListDto;
import com.btkAkademi.rentACar.business.requests.accountRequest.CreateAccountRequest;
import com.btkAkademi.rentACar.business.requests.accountRequest.UpdateAccountRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.AccountDao;
import com.btkAkademi.rentACar.entities.concretes.Account;

@Service
public class AccountManager implements AccountService {

	private AccountDao accountDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AccountManager(AccountDao accountDao, ModelMapperService modelMapperService) {
		super();
		this.accountDao = accountDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<Account> findById(int id) {
		return new SuccessDataResult<Account>(this.accountDao.findById(id));

	}

	@Override
	public Result add(CreateAccountRequest createAccountRequest) {

		Account account = modelMapperService.forRequest().map(createAccountRequest, Account.class);

		this.accountDao.save(account);
		return new SuccessResult(Messages.accountAdded);
	}

	@Override
	public DataResult<List<AccountListDto>> getAll() {
		List<Account> accountList = this.accountDao.findAll();
		List<AccountListDto> response = accountList.stream()
				.map(account -> modelMapperService.forDto().map(account, AccountListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<AccountListDto>>(response);
	}

	@Override
	public Result update(UpdateAccountRequest updateAccountRequest) {

		if (this.accountDao.existsById(updateAccountRequest.getId())) {
			Account account = this.accountDao.findById(updateAccountRequest.getId());

			account = this.modelMapperService.forRequest().map(updateAccountRequest, Account.class);
			this.accountDao.save(account);
			return new SuccessResult(Messages.accountUpdated);
		}
		return new ErrorResult(Messages.accountIsNotFound);

	}

	@Override
	public Result deleteById(int id) {
		if (this.accountDao.existsById(id)) {
			this.accountDao.deleteById(id);
			return new SuccessResult(Messages.accountDeleted);
		}
		return new ErrorResult(Messages.accountIsNotFound);
	}

}
