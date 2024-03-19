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
	
	@Autowired // �N�쥻��� Spring boot �U�ު�����(class�Binterface�B...) ���Өϥ�
	private MealDao mealDao; // �����ݩʪ��ŧi�覡

	@Override
	public void addMeal(Meal meal) {
		// save: �N��Ʀs�iDB�F
		// �p�G PKey �w�s�b�A�|��s�w�s�b�����
		// PKey ���s�b�A�|�s�W�@�����
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
		//��n��s����ƬO�_�w�s�b
		Optional<Meal> op = mealDao.findById(meal.getName());
		//�@�Ӫ���Q Optional �]�_�ӡA�D�n�O�j��h�P�_�q��Ʈw���^������O�_�� null
		if(op.isEmpty()) { //�h�P�_�Q Optional �]�_�Ӫ�����O�_�� null
			System.out.println("Not found");
			return;
		}
		Meal res = op.get(); // ���X�Q Optional �]�_�Ӫ�����
		//===============================
		System.out.println("��s�e " + res.getName() + ": " + res.getPrice());
		mealDao.save(meal);
		System.out.println("��s�� " + meal.getName() + ": " + meal.getPrice());
	}

	@Override
	public void findById(String id) {
		Optional<Meal> op = mealDao.findById(id);
		if (op.isEmpty()) {
			System.out.println(id + "���s�b");
		} else {
			System.out.println(id + "�s�b");
		}
		
	}

	@Override
	public void existsById(String id) {
		boolean res = mealDao.existsById(id);
		if (res) { //res == true
			System.out.println(id + "�s�b");
		} else { // res == flase
			System.out.println(id + "���s�b");
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
