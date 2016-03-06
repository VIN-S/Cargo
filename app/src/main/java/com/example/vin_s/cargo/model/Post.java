package com.example.vin_s.cargo.model;

//this is the post model
//every form needs a setter and a getter

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Post implements Serializable
{
	//forms
	String id;
	String OwnerID;
	String title;
	String slogan;
	String carType;
	int numberOfSeats;
	int seatsLeft;
	String details;
	String duration;
	String requirements;
	String origin;
	String dest;
	Date date;

	//constructor, add any other combinations of input that you might need
	public Post(){
		this.id = "po" + UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Post(String ownerID, String title, String slogan, String carType, int numberOfSeats, int seatsLeft, String details, String duration, String requirements, String origin, String dest, Date date) {
		this.id = "po" + UUID.randomUUID().toString();
		OwnerID = ownerID;
		this.title = title;
		this.slogan = slogan;
		this.carType = carType;
		this.numberOfSeats = numberOfSeats;
		this.seatsLeft = seatsLeft;
		this.details = details;
		this.duration = duration;
		this.requirements = requirements;
		this.origin = origin;
		this.dest = dest;
		this.date = date;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public int getSeatsLeft() {
		return seatsLeft;
	}

	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
}