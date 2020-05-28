package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;


public class _Locations {

	@Json(name = "elevation")
	protected int elevation;

	@Json(name = "recordTime")
	protected long recordTime;

	@Json(name = "hikeId")
	protected int hikeId;

	@Json(name = "hikerId")
	protected int hikerId;

	@Json(name = "latpt")
	protected double latpt;

	@Json(name = "lngpt")
	protected double lngpt;

	@Json(name = "battery")
	protected int battery;

	@Json(name = "network")
	protected int network;

	@Json(name = "elapsedTime")
	protected long elapsedTime;

	public void setElevation(int elevation){
		this.elevation = elevation;
	}

	public int getElevation(){
		return elevation;
	}

	public void setRecordTime(long recordTime){
		this.recordTime = recordTime;
	}

	public long getRecordTime(){
		return recordTime;
	}

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

	public void setBattery(int battery){
		this.battery = battery;
	}

	public int getBattery(){
		return battery;
	}

	public void setNetwork(int network){
		this.network = network;
	}

	public int getNetwork(){
		return network;
	}

	public void setElapsedTime(long elapsedTime){
		this.elapsedTime = elapsedTime;
	}

	public long getElapsedTime(){
		return elapsedTime;
	}
}