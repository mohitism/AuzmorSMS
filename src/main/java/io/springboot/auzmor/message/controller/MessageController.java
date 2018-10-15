package io.springboot.auzmor.message.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.springboot.auzmor.message.account.entity.AccountEntity;
import io.springboot.auzmor.message.account.repository.AccountDAOService;
import io.springboot.auzmor.message.constants.MessageConstants;
import io.springboot.auzmor.message.constants.MessageResponse;
import io.springboot.auzmor.message.phonenumber.entity.PhoneNumberEntity;
import io.springboot.auzmor.message.phonenumber.service.PhoneNumberDAOService;
import io.springboot.auzmor.message.redis.model.PhoneNumberData;
import io.springboot.auzmor.message.redis.repository.RedisRepository;

@RestController
public class MessageController {
	
	@Autowired
	PhoneNumberDAOService phoneNumberService;
	
	@Autowired
	AccountDAOService accountService;
	
	@Autowired
    private RedisRepository redisRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/inbound/sms", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public MessageResponse getInboundSMS(@RequestHeader("username") String username,@RequestHeader("password") String password,@RequestBody String apirequestBodyasJson) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject requestObject = (JSONObject)  parser.parse(apirequestBodyasJson);

			for (String param : MessageConstants.INBOUND_SMS_REQUEST_DATA_PARAMETERS) {

				if (requestObject.get(param) == null) {
					return new MessageResponse(":", param + " is missing");
				}
			}

			String fromNumber = (String) requestObject.get(MessageConstants.FROM);
			String toNumber = (String) requestObject.get(MessageConstants.TO);
			String text = (String) requestObject.get(MessageConstants.TEXT);

			if (fromNumber.length() < MessageConstants.FROMMINLENGTH
					|| fromNumber.length() > MessageConstants.FROMMAXLENGTH) {
				return new MessageResponse(":", MessageConstants.FROM + " is invalid");
			}

			if (toNumber.length() < MessageConstants.TOMINLENGTH || toNumber.length() > MessageConstants.TOMAXLENGTH) {
				return new MessageResponse(":", MessageConstants.TO + " is invalid");
			}

			if (text.length() < MessageConstants.TEXTMINLENGTH || text.length() > MessageConstants.TEXTMAXLENGTH) {
				return new MessageResponse(":", MessageConstants.TEXT + " is invalid");
			}
			
			AccountEntity account = this.accountService.findByUsernameAndPassword(username,password);
			
			if(account == null) {
				return new MessageResponse("","Account does not Exist");
			}else {
				PhoneNumberEntity toExistedNumber = this.phoneNumberService.findByPhoneNumberAndAccountId(toNumber,account.getId());
				if(toExistedNumber == null) {
					return new MessageResponse(":",MessageConstants.TO + " parameter not found");
				}
			}
			
			if(text.contains("STOP") || text.contains("STOP\\n") || text.contains("STOP\\r") || text.contains("STOP\\r\\n")) {
				this.redisRepository.add(new PhoneNumberData(fromNumber,toNumber));
			}
			
			return new MessageResponse("“inbound sms ok",":");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new MessageResponse("","unknown failure");
		}
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/outbound/sms", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public MessageResponse getOutboundSMS(@RequestHeader("username") String username,@RequestHeader("password") String password,@RequestBody String apirequestBodyasJson) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject requestObject = (JSONObject)  parser.parse(apirequestBodyasJson);

			for (String param : MessageConstants.OUTBOUND_SMS_REQUEST_DATA_PARAMETERS) {

				if (requestObject.get(param) == null) {
					return new MessageResponse(":", param + " is missing");
				}
			}

			String fromNumber = (String) requestObject.get(MessageConstants.FROM);
			String toNumber = (String) requestObject.get(MessageConstants.TO);
			String text = (String) requestObject.get(MessageConstants.TEXT);

			if (fromNumber.length() < MessageConstants.FROMMINLENGTH
					|| fromNumber.length() > MessageConstants.FROMMAXLENGTH) {
				return new MessageResponse(":", MessageConstants.FROM + " is invalid");
			}

			if (toNumber.length() < MessageConstants.TOMINLENGTH || toNumber.length() > MessageConstants.TOMAXLENGTH) {
				return new MessageResponse(":", MessageConstants.TO + " is invalid");
			}

			if (text.length() < MessageConstants.TEXTMINLENGTH || text.length() > MessageConstants.TEXTMAXLENGTH) {
				return new MessageResponse(":", MessageConstants.TEXT + " is invalid");
			}
			
			AccountEntity account = this.accountService.findByUsernameAndPassword(username,password);
			
			if(account == null) {
				return new MessageResponse("","Account does not Exist");
			}else {
				PhoneNumberEntity fromExistedNumber = this.phoneNumberService.findByPhoneNumberAndAccountId(fromNumber,account.getId());
				if(fromExistedNumber == null) {
					return new MessageResponse(":",MessageConstants.FROM + " parameter not found");
				}
			
			}
			
			if(text.contains("STOP") || text.contains("STOP\\n") || text.contains("STOP\\r") || text.contains("STOP\\r\\n")) {
				Map<Integer, PhoneNumberData> phoneNumberDatas = this.redisRepository.findAllPhoneNumber();
				for(Map.Entry<Integer, PhoneNumberData> entry : phoneNumberDatas.entrySet()){
		            PhoneNumberData phoneData = entry.getValue();
		            if(phoneData.getFromNumber().equals(fromNumber) && phoneData.getToNumber().equals(toNumber)) {
		            	return new MessageResponse(":","sms from "+fromNumber+ " to " +toNumber+" blocked by STOP request");
		            }
		        }
			}
			if(this.redisRepository.getNumberOfHits(fromNumber)!=null && this.redisRepository.getNumberOfHits(fromNumber) > 50) {
				return new MessageResponse(":","limit reached for "+MessageConstants.FROM);
			}
			Integer number = this.redisRepository.getNumberOfHits(fromNumber);
			this.redisRepository.addRequestHit(fromNumber);
			
			return new MessageResponse("““outbound sms ok",":");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new MessageResponse("","unknown failure");
		}
	}
	
	
	
}
