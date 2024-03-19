package com.example.practice.service.ifs;

import com.example.practice.entity.NewMeal;

public interface NewMealService {
	
	public void addNewMeal(NewMeal newMeal);
	
	public void updateNewMeal(NewMeal newMeal);
	
	public void findByNameAndCookingStyle(String name, String cookingStyle);

}
