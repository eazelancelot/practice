package com.example.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.Meal;

@Repository // 將 MealDao 交由 Spring boot 託管成 repository 類
//Dao(Data access object)
public interface MealDao extends JpaRepository<Meal, String> {
	
	//自定義的Dao方法(只需要定義方法名稱，不需要實作)
	// 1. 小駝峰命名; 2. By的B要大寫 3. By 後面的名稱要與類別 Meal 裏面的變數名稱完全一樣
	public Meal findByName(String name);
	
	public Meal findByPrice(int price);
	
	public Meal findByNameAndPrice(String name, int price);
	
	public List<Meal> findByNameOrPrice(String name, int price);

}
