package com.example.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.practice.entity.NewMeal;
import com.example.practice.service.ifs.NewMealService;

@SpringBootTest
public class NewMealServiceTest {
	
	@Autowired
	private NewMealService newMealService;
	
	@Test
	public void addTest() {
		newMealService.addNewMeal(new NewMeal("�T���v", "��", 30));
	}
	
	@Test
	public void updateTest() {
		
	}
	
	@Test
	public void findTest() {
		newMealService.findByNameAndCookingStyle("�T���v", "��");
	}

}
