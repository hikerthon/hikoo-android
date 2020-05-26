package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class _Shelter {

	@Json(name = "name")
	protected String name;

	@Json(name = "id")
	protected int id;

	@Json(name = "latpt")
	protected long latpt;

	@Json(name = "lngpt")
	protected long lngpt;

	@Json(name = "capacity")
	protected int capacity;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLatpt(long latpt){
		this.latpt = latpt;
	}

	public long getLatpt(){
		return latpt;
	}

	public void setLngpt(long lngpt){
		this.lngpt = lngpt;
	}

	public long getLngpt(){
		return lngpt;
	}

	public void setCapacity(int capacity){
		this.capacity = capacity;
	}

	public int getCapacity(){
		return capacity;
	}
}