package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class Location{

	@Json(name = "lng")
	private int lng;

	@Json(name = "userId")
	private int userId;

	@Json(name = "lat")
	private int lat;

	public int getLng(){
		return lng;
	}

	public int getUserId(){
		return userId;
	}

	public int getLat(){
		return lat;
	}
}