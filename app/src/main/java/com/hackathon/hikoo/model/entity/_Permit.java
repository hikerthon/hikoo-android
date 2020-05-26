package com.hackathon.hikoo.model.entity;

import java.util.List;

import com.hackathon.hikoo.model.domain.Hik;
import com.squareup.moshi.Json;

public class _Permit {

	@Json(name = "trailList")
	protected List<String> trailList;

	@Json(name = "permitId")
	protected int permitId;

	@Json(name = "permitName")
	protected String permitName;

	@Json(name = "hik")
	protected Hik hik;

	public void setTrailList(List<String> trailList){
		this.trailList = trailList;
	}

	public List<String> getTrailList(){
		return trailList;
	}

	public void setPermitId(int permitId){
		this.permitId = permitId;
	}

	public int getPermitId(){
		return permitId;
	}

	public void setPermitName(String permitName){
		this.permitName = permitName;
	}

	public String getPermitName(){
		return permitName;
	}

	public void setHik(Hik hik){
		this.hik = hik;
	}

	public Hik getHik(){
		return hik;
	}
}