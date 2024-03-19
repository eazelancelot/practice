package com.example.practice.vo;

import com.example.practice.constants.RtnCode;
import com.example.practice.entity.Atm;

public class AtmRes {
	
	private RtnCode rtnCode;

	private Atm atm;

	public AtmRes() {
		super();
	}

	public AtmRes(RtnCode rtnCode, Atm atm) {
		super();
		this.rtnCode = rtnCode;
		this.atm = atm;
	}

	public RtnCode getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(RtnCode rtnCode) {
		this.rtnCode = rtnCode;
	}

	public Atm getAtm() {
		return atm;
	}

	public void setAtm(Atm atm) {
		this.atm = atm;
	}

}
