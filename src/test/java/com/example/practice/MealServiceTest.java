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
		mealService.addMeal(new Meal("奶茶", 25));
	}
	
	@Test
	public void addMealsTest() {
		List<Meal> list = new ArrayList<>();
		list.add(new Meal("三明治", 30));
		list.add(new Meal("漢堡", 40));
		list.add(new Meal("奶茶", 20));
		mealService.addMeals(list);
	}
	
	@Test
	public void updateMealTest() {		
		mealService.updateMeal(new Meal("起司漢堡", 60));
		mealService.updateMeal(new Meal("漢堡", 60));
	}
	
	@Test
	public void existsByIdTest() {
		mealService.existsById("起司漢堡");
		mealService.existsById("漢堡");
	}
	
	@Test
	public void findByNameTest() {
		mealService.findByName("起司漢堡");
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
