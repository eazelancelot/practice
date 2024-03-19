package com.example.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.NewMeal;
import com.example.practice.entity.NewMealId;

@Repository
public interface NewMealDao extends JpaRepository<NewMeal, NewMealId> {
	
	public NewMeal findByNameAndCookingStyle(String name, String cookingStyle);

}
