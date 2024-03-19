package com.example.practice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // �N Meal ��� Spring boot �U�ަ�������(Entity)
@Table(name = "meal") // �n���D�O��������i��
public class Meal {

	@Id // �]�������b���O PKey�A�ҥH�n�[�W @Id
	@Column(name = "name") // �o�䪺�r�� name �O DB table �������W��(�n�����@�Ҥ@��)
	private String name; // �o�䪺�ܼƦW�� name �O�ݩʦW��

	@Column(name = "price")
	private int price;

	public Meal() {
		super();
	}

	public Meal(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
