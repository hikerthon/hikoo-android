package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class _Alert {

	@Json(name = "alertLevelId")
	protected int alertLevelId;

	@Json(name = "permitId")
	protected int permitId;

	@Json(name = "creatorId")
	protected int creatorId;

	@Json(name = "originEventId")
	protected int originEventId;

	@Json(name = "lngpt")
	protected long lngpt;

	@Json(name = "eventTypeId")
	protected int eventTypeId;

	@Json(name = "eventInfo")
	protected String eventInfo;

	@Json(name = "eventEnd")
	protected String eventEnd;

	@Json(name = "eventTime")
	protected String eventTime;

	@Json(name = "id")
	protected int id;

	@Json(name = "latpt")
	protected long latpt;

	@Json(name = "radius")
	protected int radius;

	@Json(name = "logtime")
	protected String logtime;

	public void setAlertLevelId(int alertLevelId){
		this.alertLevelId = alertLevelId;
	}

	public int getAlertLevelId(){
		return alertLevelId;
	}

	public void setPermitId(int permitId){
		this.permitId = permitId;
	}

	public int getPermitId(){
		return permitId;
	}

	public void setCreatorId(int creatorId){
		this.creatorId = creatorId;
	}

	public int getCreatorId(){
		return creatorId;
	}

	public void setOriginEventId(int originEventId){
		this.originEventId = originEventId;
	}

	public int getOriginEventId(){
		return originEventId;
	}

	public void setLngpt(long lngpt){
		this.lngpt = lngpt;
	}

	public long getLngpt(){
		return lngpt;
	}

	public void setEventTypeId(int eventTypeId){
		this.eventTypeId = eventTypeId;
	}

	public int getEventTypeId(){
		return eventTypeId;
	}

	public void setEventInfo(String eventInfo){
		this.eventInfo = eventInfo;
	}

	public String getEventInfo(){
		return eventInfo;
	}

	public void setEventEnd(String eventEnd){
		this.eventEnd = eventEnd;
	}

	public String getEventEnd(){
		return eventEnd;
	}

	public void setEventTime(String eventTime){
		this.eventTime = eventTime;
	}

	public String getEventTime(){
		return eventTime;
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

	public void setRadius(int radius){
		this.radius = radius;
	}

	public int getRadius(){
		return radius;
	}

	public void setLogtime(String logtime){
		this.logtime = logtime;
	}

	public String getLogtime(){
		return logtime;
	}
}