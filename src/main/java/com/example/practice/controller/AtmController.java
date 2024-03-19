package com.example.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice.service.ifs.AtmService;
import com.example.practice.vo.AtmReq;
import com.example.practice.vo.AtmRes;
import com.example.practice.vo.UpdatePwdReq;

@RestController  // 等同於 @Controller + 在 AtmRes 前面加上 @ResponseBody
//@Controller
public class AtmController {
	
	@Autowired
	private AtmService atmService;
	
//	@RequestMapping(value = "atm/add", method = RequestMethod.POST)
	@PostMapping(value = "atm/add")
	public AtmRes addInfo(@RequestBody AtmReq req) {
		return atmService.addInfo(req.getAccount(), req.getPwd(), req.getAmount());
	}
	
	@GetMapping(value = "atm/get_amount")
	public AtmRes getAmount(@RequestBody AtmReq req) {
		return atmService.getAmountByAccount(req.getAccount(), req.getPwd());
	}
	
	@GetMapping(value = "atm/get_amount1")
	public AtmRes getAmount1( //
			// value 後面的字串，等同於@JsonProperty的功用
			// required 表示這個參數是否為必須，預設為 true；所以下面的 required = true 可以省略
			// defaultValue 表示沒有輸入此參數的值時給的預設值；預設值是空字串
			@RequestParam(value = "account", required = true, defaultValue = "") String account, //
			@RequestParam(value = "password") String pwd, //
	        @RequestParam(value = "amount") int amount) {
		return atmService.getAmountByAccount(account, pwd);
	}
	
	// {account}_{password}: 
	// 大括號中的2個字串要與方法中的變數名稱一模一樣，底線用於串接多個變數
	// 底線(_)只是串接2個不同變數，方便在輸入 URI 時容易區分，不限一定要使用底線，所以底線可以改成用逗號
	// 例如: localhost:8080/atm/get_amount2/A01_AA123
	@GetMapping(value = "atm/get_amount2/{account},{password}")
	public AtmRes getAmount2( //
			// required 表示這個參數是否為必須，預設為 true；所以下面的 required = true 可以省略
			@PathVariable(value = "account", required = true) String account, //
			@PathVariable(value = "password") String pwd) {
		return atmService.getAmountByAccount(account, pwd);
	}
	
	@PostMapping(value = "atm/update_password")
	public AtmRes updatePassword(@RequestBody UpdatePwdReq req) {
		return atmService.updatePassword(req.getAccount(), req.getOldPwd(), req.getNewPwd());
	}
	
	@PostMapping(value = "atm/deposit")
	public AtmRes deposit(@RequestBody AtmReq req) {
		return atmService.deposit(req.getAccount(), req.getPwd(), req.getAmount());
	}
	
	@PostMapping(value = "atm/withdraw")
	public AtmRes withdraw(@RequestBody AtmReq req) {
		return atmService.withdraw(req.getAccount(), req.getPwd(), req.getAmount());
	}

}
