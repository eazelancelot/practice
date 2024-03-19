package com.example.practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.Meal;

@Repository // �N MealDao ��� Spring boot �U�ަ� repository ��
//Dao(Data access object)
public interface MealDao extends JpaRepository<Meal, String> {
	
	//�۩w�q��Dao��k(�u�ݭn�w�q��k�W�١A���ݭn��@)
	// 1. �p�m�p�R�W; 2. By��B�n�j�g 3. By �᭱���W�٭n�P���O Meal �ح����ܼƦW�٧����@��
	public Meal findByName(String name);
	
	public Meal findByPrice(int price);
	
	public Meal findByNameAndPrice(String name, int price);
	
	public List<Meal> findByNameOrPrice(String name, int price);

}
