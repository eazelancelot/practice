package com.example.practice.service.ifs;

import java.util.List;

import com.example.practice.entity.Meal;

public interface MealService {
	
	public void addMeal(Meal meal);
	
	public void addMeals(List<Meal> meals);
	
	public void updateMeal(Meal meal);
	
	public void findById(String id);
	
	public void existsById(String id);
	
	public void findByName(String name);
	
	public void findByPrice(int price);
	
	public void findAll();

}
