package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class _Shelter {

	@Json(name = "shelterName")
	protected String shelterName;

	@Json(name = "id")
	protected int id;

	@Json(name = "latpt")
	protected double latpt;

	@Json(name = "lngpt")
	protected double lngpt;

	@Json(name = "capacity")
	protected int capacity;

	public void setShelterName(String shelterName){
		this.shelterName = shelterName;
	}

	public String getShelterName(){
		return shelterName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLatpt(double latpt){
		this.latpt = latpt;
	}

	public double getLatpt(){
		return latpt;
	}

	public void setLngpt(double lngpt){
		this.lngpt = lngpt;
	}

	public double getLngpt(){
		return lngpt;
	}

	public void setCapacity(int capacity){
		this.capacity = capacity;
	}

	public int getCapacity(){
		return capacity;
	}
}