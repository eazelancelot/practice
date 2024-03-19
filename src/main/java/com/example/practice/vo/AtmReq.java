package com.example.practice.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

// 另一種寫法:
// 此類別中的3個屬性剛好與類別 Atm 的3個屬性一模一樣，所以也可以使用 extends Atm；
// 若使用 extends Atm，則此類別中的3個屬性就不需要寫，只要新增建構方法(點選預設建構方法)即可
public class AtmReq {

	private String account;

	// 以下2個 annotation 是讓外部輸入的 key 的名稱可以對應到此 Req 中的屬性名稱 pwd
//	@JsonProperty("password")  // 一(外面輸入的 key 名稱 password)對一(此類別中的屬性名稱 pwd)
	@JsonAlias({"password", "pwd"}) // 多對一
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
