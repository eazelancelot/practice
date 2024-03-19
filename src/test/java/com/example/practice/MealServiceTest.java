package com.example.practice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.practice.entity.Meal;
import com.example.practice.service.ifs.MealService;

@SpringBootTest
public class MealServiceTest {
	
	@Autowired
	private MealService mealService;
	
	@Test
	public void addMealTest() {
		mealService.addMeal(new Meal("����", 25));
	}
	
	@Test
	public void addMealsTest() {
		List<Meal> list = new ArrayList<>();
		list.add(new Meal("�T���v", 30));
		list.add(new Meal("�~��", 40));
		list.add(new Meal("����", 20));
		mealService.addMeals(list);
	}
	
	@Test
	public void updateMealTest() {		
		mealService.updateMeal(new Meal("�_�q�~��", 60));
		mealService.updateMeal(new Meal("�~��", 60));
	}
	
	@Test
	public void existsByIdTest() {
		mealService.existsById("�_�q�~��");
		mealService.existsById("�~��");
	}
	
	@Test
	public void findByNameTest() {
		mealService.findByName("�_�q�~��");
	}
	
	@Test
	public void findByPriceTest() {
		mealService.findByPrice(100);
	}
	
	@Test
	public void findAllTest() {
		mealService.findAll();
	}

}
