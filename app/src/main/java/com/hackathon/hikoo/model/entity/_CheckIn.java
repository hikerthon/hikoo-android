package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class _CheckIn {

	@Json(name = "hikeId")
	protected int hikeId;

	@Json(name = "hikerId")
	protected int hikerId;

	public void setHikeId(int hikeId){
		this.hikeId = hikeId;
	}

	public int getHikeId(){
		return hikeId;
	}

	public void setHikerId(int hikerId){
		this.hikerId = hikerId;
	}

	public int getHikerId(){
		return hikerId;
	}
}