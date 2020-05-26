package com.hackathon.hikoo.model.entity;

import java.util.List;
import com.squareup.moshi.Json;

public class _Hik {

	@Json(name = "permitAccepted")
	protected boolean permitAccepted;

	@Json(name = "trailList")
	protected List<String> trailList;

	@Json(name = "permitId")
	protected int permitId;

	@Json(name = "permitName")
	protected String permitName;

	@Json(name = "hikeId")
	protected int hikeId;

	@Json(name = "hikeEndTime")
	protected String hikeEndTime;

	@Json(name = "hikerName")
	protected String hikerName;

	@Json(name = "hikerAge")
	protected int hikerAge;

	@Json(name = "hikeFinished")
	protected boolean hikeFinished;

	@Json(name = "hikerGender")
	protected String hikerGender;

	@Json(name = "hikerNationality")
	protected String hikerNationality;

	@Json(name = "hikerId")
	protected int hikerId;

	@Json(name = "hikeStarted")
	protected boolean hikeStarted;

	@Json(name = "hikeStartTime")
	protected String hikeStartTime;

	public void setPermitAccepted(boolean permitAccepted){
		this.permitAccepted = permitAccepted;
	}

	public boolean isPermitAccepted(){
		return permitAccepted;
	}

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

	public void setHikeId(int hikeId){
		this.hikeId = hikeId;
	}

	public int getHikeId(){
		return hikeId;
	}

	public void setHikeEndTime(String hikeEndTime){
		this.hikeEndTime = hikeEndTime;
	}

	public String getHikeEndTime(){
		return hikeEndTime;
	}

	public void setHikerName(String hikerName){
		this.hikerName = hikerName;
	}

	public String getHikerName(){
		return hikerName;
	}

	public void setHikerAge(int hikerAge){
		this.hikerAge = hikerAge;
	}

	public int getHikerAge(){
		return hikerAge;
	}

	public void setHikeFinished(boolean hikeFinished){
		this.hikeFinished = hikeFinished;
	}

	public boolean isHikeFinished(){
		return hikeFinished;
	}

	public void setHikerGender(String hikerGender){
		this.hikerGender = hikerGender;
	}

	public String getHikerGender(){
		return hikerGender;
	}

	public void setHikerNationality(String hikerNationality){
		this.hikerNationality = hikerNationality;
	}

	public String getHikerNationality(){
		return hikerNationality;
	}

	public void setHikerId(int hikerId){
		this.hikerId = hikerId;
	}

	public int getHikerId(){
		return hikerId;
	}

	public void setHikeStarted(boolean hikeStarted){
		this.hikeStarted = hikeStarted;
	}

	public boolean isHikeStarted(){
		return hikeStarted;
	}

	public void setHikeStartTime(String hikeStartTime){
		this.hikeStartTime = hikeStartTime;
	}

	public String getHikeStartTime(){
		return hikeStartTime;
	}
}