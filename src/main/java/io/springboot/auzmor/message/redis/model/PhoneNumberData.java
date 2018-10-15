package io.springboot.auzmor.message.redis.model;

import java.io.Serializable;

public class PhoneNumberData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static int increment = 0;
	
	private Integer id;
	
	private String fromNumber;
	
	private String toNumber;

	public PhoneNumberData(String fromNumber, String toNumber) {
		super();
		this.id = increment;
		this.fromNumber = fromNumber;
		this.toNumber = toNumber;
		increment++;
	}

	public Integer getId() {
		return id;
	}

	public String getFromNumber() {
		return fromNumber;
	}

	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}

	public String getToNumber() {
		return toNumber;
	}

	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	
}
