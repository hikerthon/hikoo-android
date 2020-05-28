package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;


public class _AroundMe {

	@Json(name = "ptinfo")
	protected String ptinfo;

	@Json(name = "event_info")
	protected String eventInfo;

	@Json(name = "distanceMeter")
	protected double distanceMeter;

	@Json(name = "latpt")
	protected double latpt;

	@Json(name = "lngpt")
	protected double lngpt;

	@Json(name = "event_type_name")
	protected String eventTypeName;

	@Json(name = "logtime")
	protected long logtime;

	@Json(name = "alert_name")
	protected String alertName;

	public void setPtinfo(String ptinfo){
		this.ptinfo = ptinfo;
	}

	public String getPtinfo(){
		return ptinfo;
	}

	public void setEventInfo(String eventInfo){
		this.eventInfo = eventInfo;
	}

	public String getEventInfo(){
		return eventInfo;
	}

	public void setDistanceMeter(double distanceMeter){
		this.distanceMeter = distanceMeter;
	}

	public double getDistanceMeter(){
		return distanceMeter;
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

	public void setEventTypeName(String eventTypeName){
		this.eventTypeName = eventTypeName;
	}

	public String getEventTypeName(){
		return eventTypeName;
	}

	public void setLogtime(long logtime){
		this.logtime = logtime;
	}

	public long getLogtime(){
		return logtime;
	}

	public void setAlertName(String alertName){
		this.alertName = alertName;
	}

	public String getAlertName(){
		return alertName;
	}
}