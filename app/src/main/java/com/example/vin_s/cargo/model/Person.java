package com.example.vin_s.cargo.model;

import java.util.UUID;

//This is the person model
public class Person
{
	String id; //assign everyone a personal int id?
	String name;
	String intro;
	
	//constructors
	public Person(){}
	
	public Person(String n, String c, String i)
	{
		id = "pe" + UUID.randomUUID().toString();;;
		name = n;
		intro = i;
	}
	
	//setters	
	public void setName(String name){
		this.name = name;
	}

	public void setIntro(String intro){
		this.intro = intro;
	}
	
	//getters 
	public String getName(){
		return name;
	}

	public String getIntro(){
		return intro;
	}
}