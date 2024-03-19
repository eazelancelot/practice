package com.example.practice.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practice.entity.NewMeal;
import com.example.practice.entity.NewMealId;
import com.example.practice.repository.NewMealDao;
import com.example.practice.service.ifs.NewMealService;

@Service
public class NewMealServiceImpl implements NewMealService {
	
	@Autowired
	private NewMealDao newMealDao;

	@Override
	public void addNewMeal(NewMeal newMeal) {
		newMealDao.save(newMeal);
		System.out.println("新增的餐點: " + newMeal.getName());
		System.out.println("餐點料理方式: " + newMeal.getCookingStyle());
		System.out.println("餐點價格: " + newMeal.getPrice());
		
	}

	@Override
	public void updateNewMeal(NewMeal newMeal) {
		Optional<NewMeal> op = newMealDao.findById(new NewMealId(newMeal.getName(), newMeal.getCookingStyle()));
		if(op.isEmpty()) {
			System.out.println("餐點找不到");
			return;
		}
		NewMeal res = op.get();
		System.out.println(res.getCookingStyle() + res.getName() + res.getPrice());
		newMealDao.save(newMeal);
		System.out.println(newMeal.getCookingStyle() + newMeal.getName() + newMeal.getPrice());
	}

	@Override
	public void findByNameAndCookingStyle(String name, String cookingStyle) {
		NewMeal res = newMealDao.findByNameAndCookingStyle(name, cookingStyle);
		if (res == null) {
			System.out.println("餐點找不到");
		} else {
			System.out.println(res.getCookingStyle() + res.getName() + res.getPrice());
		}
	}

}
