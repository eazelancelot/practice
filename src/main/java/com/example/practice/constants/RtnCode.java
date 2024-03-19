package com.example.practice.constants;

public enum RtnCode {

	SUCCESS(200, "Success!!"), //
	ACCOUNT_EXISTS(400, "Account exists!!"), //
	INPUT_INFO_ERROR(400, "Input info error!!"), //
	ACCOUNT_NOT_FOUND(404, "Account not found!!"), //
	PASSWORD_ERROR(400, "Password error!!"), //
	INSUFFICIENT_BALANCE(400, "Insufficient balance!!");

	private int code;

	private String message;

	private RtnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
