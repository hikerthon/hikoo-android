package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class _TrailsItem {

	@Json(name = "name")
	protected String name;

	@Json(name = "permit")
	protected int permit;

	@Json(name = "id")
	protected int id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPermit(int permit){
		this.permit = permit;
	}

	public int getPermit(){
		return permit;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}