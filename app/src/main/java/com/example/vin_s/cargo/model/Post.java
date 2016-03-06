package com.example.vin_s.cargo.model;

//this is the post model
//every form needs a setter and a getter

import java.util.Date;
import java.util.UUID;

public class Post
{
	//forms
	String id;
	String OwnerID;
	String origin;
	String dest;
	Date date;

	//constructor, add any other combinations of input that you might need
	public Post(){
		this.id = "po" + UUID.randomUUID().toString();
	}

	public Post(String ownerID, String origin, String dest, Date date) {
		this.id = "po" + UUID.randomUUID().toString();
		OwnerID = ownerID;
		this.origin = origin;
		this.dest = dest;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getOwnerID() {
		return OwnerID;
	}

	public void setOwnerID(String ownerID) {
		OwnerID = ownerID;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Date getDate() {
		return date;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}
}