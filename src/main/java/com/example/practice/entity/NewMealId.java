package com.example.practice.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NewMealId implements Serializable {

	private String name;

	private String cookingStyle;

	public NewMealId() {
		super();
	}

	public NewMealId(String name, String cookingStyle) {
		super();
		this.name = name;
		this.cookingStyle = cookingStyle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCookingStyle() {
		return cookingStyle;
	}

	public void setCookingStyle(String cookingStyle) {
		this.cookingStyle = cookingStyle;
	}

}
