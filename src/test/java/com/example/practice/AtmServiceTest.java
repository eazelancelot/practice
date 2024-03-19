package com.example.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.practice.repository.AtmDao;
import com.example.practice.service.ifs.AtmService;
import com.example.practice.vo.AtmRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class AtmServiceTest {
	
	@Autowired
	private AtmService atmService;
	
	@Autowired
	private AtmDao atmDao;
	
	@Test
	public void addTest() {
		//帳號不符規定
		AtmRes res = atmService.addInfo("", "AA123", 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "測試錯誤");
		//密碼不符規定
		res = atmService.addInfo("A01", null, 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "測試錯誤");
		//金額不符規定
		res = atmService.addInfo("A01", "AA123", -1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "測試錯誤");
		//帳號已存在
		res = atmService.addInfo("A01", "AA123", 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "測試錯誤");
		//正確邏輯
		res = atmService.addInfo("A03", "AA123", 5000);
		Assert.isTrue(res.getRtnCode().getCode() == 200, "測試錯誤");
		// 因為 addInfo("A02", "AA123", 1000) 是測試資料，所以測試的最後要把該筆資料刪除
//		atmDao.deleteById("A02");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String str = mapper.writeValueAsString(res);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("=============================");
	}
	
	@Test
	public void getAmountTest() {
		atmService.getAmountByAccount("A01", "AA1234");
	}
	
	@Test
	public void test() {
		String pattern = "\\W.*";
		String str = "abc";
		String str1 = "@abc";
		String str2 = "a#bc";
		String str3 = "abc!";
		System.out.println(str.matches(pattern));
		System.out.println(str1.matches(pattern));
		System.out.println(str2.matches(pattern));
		System.out.println(str3.matches(pattern));
	}

}
