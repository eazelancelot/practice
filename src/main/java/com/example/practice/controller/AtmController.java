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

@RestController  // ���P�� @Controller + �b AtmRes �e���[�W @ResponseBody
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
			// value �᭱���r��A���P��@JsonProperty���\��
			// required ��ܳo�ӰѼƬO�_�������A�w�]�� true�F�ҥH�U���� required = true �i�H�ٲ�
			// defaultValue ��ܨS����J���Ѽƪ��Ȯɵ����w�]�ȡF�w�]�ȬO�Ŧr��
			@RequestParam(value = "account", required = true, defaultValue = "") String account, //
			@RequestParam(value = "password") String pwd, //
	        @RequestParam(value = "amount") int amount) {
		return atmService.getAmountByAccount(account, pwd);
	}
	
	// {account}_{password}: 
	// �j�A������2�Ӧr��n�P��k�����ܼƦW�٤@�Ҥ@�ˡA���u�Ω�걵�h���ܼ�
	// ���u(_)�u�O�걵2�Ӥ��P�ܼơA��K�b��J URI �ɮe���Ϥ��A�����@�w�n�ϥΩ��u�A�ҥH���u�i�H�令�γr��
	// �Ҧp: localhost:8080/atm/get_amount2/A01_AA123
	@GetMapping(value = "atm/get_amount2/{account},{password}")
	public AtmRes getAmount2( //
			// required ��ܳo�ӰѼƬO�_�������A�w�]�� true�F�ҥH�U���� required = true �i�H�ٲ�
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
