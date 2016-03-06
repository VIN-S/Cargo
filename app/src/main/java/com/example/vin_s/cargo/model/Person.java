package com.example.vin_s.cargo.model;

import java.io.Serializable;
import java.util.UUID;

//This is the person model
public class Person implements Serializable
{
	String id;
    String email;
	String name;
	String intro;
    String password;
	
	//constructors
	public Person(){
		this.id = "pe" + UUID.randomUUID().toString();
	}
	
	public Person(String email, String name, String intro, String password)
	{
		this.id = "pe" + UUID.randomUUID().toString();
		this.email = email;
        this.name = name;
		this.intro = intro;
        this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {this.id = id;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}