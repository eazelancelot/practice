package com.example.practice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practice.entity.Meal;
import com.example.practice.repository.MealDao;
import com.example.practice.service.ifs.MealService;

@Service
public class MealServiceImpl implements MealService {
	
	@Autowired // 將原本交由 Spring boot 託管的物件(class、interface、...) 拿來使用
	private MealDao mealDao; // 類似屬性的宣告方式

	@Override
	public void addMeal(Meal meal) {
		// save: 將資料存進DB；
		// 如果 PKey 已存在，會更新已存在的資料
		// PKey 不存在，會新增一筆資料
		mealDao.save(meal);
		System.out.println(meal.getName() + ": " + meal.getPrice());
	}

	@Override
	public void addMeals(List<Meal> meals) {
		mealDao.saveAll(meals);
		for(Meal item : meals) {
			System.out.println(item.getName() + ": " + item.getPrice());
		}
	}

	@Override
	public void updateMeal(Meal meal) {
		//找要更新的資料是否已存在
		Optional<Meal> op = mealDao.findById(meal.getName());
		//一個物件被 Optional 包起來，主要是強制去判斷從資料庫取回的物件是否為 null
		if(op.isEmpty()) { //去判斷被 Optional 包起來的物件是否為 null
			System.out.println("Not found");
			return;
		}
		Meal res = op.get(); // 取出被 Optional 包起來的物件
		//===============================
		System.out.println("更新前 " + res.getName() + ": " + res.getPrice());
		mealDao.save(meal);
		System.out.println("更新後 " + meal.getName() + ": " + meal.getPrice());
	}

	@Override
	public void findById(String id) {
		Optional<Meal> op = mealDao.findById(id);
		if (op.isEmpty()) {
			System.out.println(id + "不存在");
		} else {
			System.out.println(id + "存在");
		}
		
	}

	@Override
	public void existsById(String id) {
		boolean res = mealDao.existsById(id);
		if (res) { //res == true
			System.out.println(id + "存在");
		} else { // res == flase
			System.out.println(id + "不存在");
		}		
	}

	@Override
	public void findByName(String name) {
		Meal res = mealDao.findByName(name);
		if (res == null) {
			System.out.println("Not found");
			return;
		}
		System.out.println(res.getName() + ": " + res.getPrice());
	}

	@Override
	public void findByPrice(int price) {
		Meal res = mealDao.findByPrice(100);
		if (res == null) {
			System.out.println("Not found");
			return;
		}
		System.out.println(res.getName() + ": " + res.getPrice());
	}

	@Override
	public void findAll() {
		List<Meal> res = mealDao.findAll();
		for(Meal item : res) {
			System.out.println(item.getName() + ": " + item.getPrice());
		}
	}

}
