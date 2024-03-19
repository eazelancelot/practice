package com.example.practice.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

// �t�@�ؼg�k:
// �����O����3���ݩʭ�n�P���O Atm ��3���ݩʤ@�Ҥ@�ˡA�ҥH�]�i�H�ϥ� extends Atm�F
// �Y�ϥ� extends Atm�A�h�����O����3���ݩʴN���ݭn�g�A�u�n�s�W�غc��k(�I��w�]�غc��k)�Y�i
public class AtmReq {

	private String account;

	// �H�U2�� annotation �O���~����J�� key ���W�٥i�H�����즹 Req �����ݩʦW�� pwd
//	@JsonProperty("password")  // �@(�~����J�� key �W�� password)��@(�����O�����ݩʦW�� pwd)
	@JsonAlias({"password", "pwd"}) // �h��@
	private String pwd;

	private int amount;

	public AtmReq() {
		super();
	}

	public AtmReq(String account, String pwd, int amount) {
		super();
		this.account = account;
		this.pwd = pwd;
		this.amount = amount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
