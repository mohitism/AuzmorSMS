package io.springboot.auzmor.message.account.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.springboot.auzmor.message.account.entity.AccountEntity;

@Service(value="accountDAOService")
public class AccountDAOService {
	
	@Autowired
	AccountRepository accountRepository;
	
	public AccountEntity findByUsernameAndPassword(String username,String password) {
		// TODO Auto-generated method stub
		return accountRepository.findByUsernameAndPassword(username,password);
	}

}
