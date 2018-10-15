package io.springboot.auzmor.message.phonenumber.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.springboot.auzmor.message.phonenumber.entity.PhoneNumberEntity;

@Service(value="phoneNumberDAOService")
public class PhoneNumberDAOService {
	
	@Autowired
	PhoneNumberRepository phoneNumberRepository;
	
	public PhoneNumberEntity findByPhoneNumberAndAccountId(String phoneNumber,Integer accountId) {
		// TODO Auto-generated method stub
		return this.phoneNumberRepository.findByPhoneNumberAndAccountId(phoneNumber , accountId);
	}
	
	public List<PhoneNumberEntity> findByAccountId(Integer accountId){
		return this.phoneNumberRepository.findByAccountId(accountId);
	}

}
