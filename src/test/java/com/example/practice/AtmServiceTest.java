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
		//�b�����ųW�w
		AtmRes res = atmService.addInfo("", "AA123", 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "���տ��~");
		//�K�X���ųW�w
		res = atmService.addInfo("A01", null, 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "���տ��~");
		//���B���ųW�w
		res = atmService.addInfo("A01", "AA123", -1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "���տ��~");
		//�b���w�s�b
		res = atmService.addInfo("A01", "AA123", 1000);
		Assert.isTrue(res.getRtnCode().getCode() != 200, "���տ��~");
		//���T�޿�
		res = atmService.addInfo("A03", "AA123", 5000);
		Assert.isTrue(res.getRtnCode().getCode() == 200, "���տ��~");
		// �]�� addInfo("A02", "AA123", 1000) �O���ո�ơA�ҥH���ժ��̫�n��ӵ���ƧR��
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
