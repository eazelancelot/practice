package com.example.practice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // 將 Meal 交由 Spring boot 託管成實體類(Entity)
@Table(name = "meal") // 要知道是對應到哪張表
public class Meal {

	@Id // 因為此欄位在表中是 PKey，所以要加上 @Id
	@Column(name = "name") // 這邊的字串 name 是 DB table 中的欄位名稱(要完全一模一樣)
	private String name; // 這邊的變數名稱 name 是屬性名稱

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
