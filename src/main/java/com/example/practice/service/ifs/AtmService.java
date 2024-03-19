package com.example.practice.service.ifs;

import com.example.practice.vo.AtmRes;

public interface AtmService {
	
	public AtmRes addInfo(String account, String pwd, int amount);
	
	public AtmRes getAmountByAccount(String account, String pwd);
	
	public AtmRes updatePassword(String account, String oldPwd, String newPwd);
	
	public AtmRes deposit(String account, String pwd, int amount);
	
	public AtmRes withdraw(String account, String pwd, int amount);

}
