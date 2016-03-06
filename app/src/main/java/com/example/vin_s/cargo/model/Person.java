package com.example.vin_s.cargo.model;

import java.util.UUID;

//This is the person model
public class Person
{
	String id; //assign everyone a personal int id?
	String name;
	String intro;
	
	//constructors
	public Person(){
		this.id = "pe" + UUID.randomUUID().toString();
	}
	
	public Person(String n, String i)
	{
		id = "pe" + UUID.randomUUID().toString();
		name = n;
		intro = i;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
}