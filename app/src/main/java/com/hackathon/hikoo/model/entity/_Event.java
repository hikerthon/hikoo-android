package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class _Event {

	@Json(name = "eventId")
	protected int eventId;

	@Json(name = "hikeId")
	protected int hikeId;

	@Json(name = "alertLevel")
	protected String alertLevel;

	@Json(name = "reporter")
	protected String reporter;

	@Json(name = "eventType")
	protected String eventType;

	@Json(name = "ttl")
	protected int ttl;

	@Json(name = "long")
	protected long lng;

	@Json(name = "eventInfo")
	protected String eventInfo;

	@Json(name = "eventTime")
	protected long eventTime;

	@Json(name = "hikerId")
	protected int hikerId;

	@Json(name = "alertId")
	protected int alertId;

	@Json(name = "radius")
	protected int radius;

	@Json(name = "lat")
	protected long lat;

	@Json(name = "status")
	protected int status;

	public void setEventId(int eventId){
		this.eventId = eventId;
	}

	public int getEventId(){
		return eventId;
	}

	public void setHikeId(int hikeId){
		this.hikeId = hikeId;
	}

	public int getHikeId(){
		return hikeId;
	}

	public void setAlertLevel(String alertLevel){
		this.alertLevel = alertLevel;
	}

	public String getAlertLevel(){
		return alertLevel;
	}

	public void setReporter(String reporter){
		this.reporter = reporter;
	}

	public String getReporter(){
		return reporter;
	}

	public void setEventType(String eventType){
		this.eventType = eventType;
	}

	public String getEventType(){
		return eventType;
	}

	public void setTtl(int ttl){
		this.ttl = ttl;
	}

	public int getTtl(){
		return ttl;
	}

	public void setLng(long lng){
		this.lng = lng;
	}

	public long getLng(){
		return lng;
	}

	public void setEventInfo(String eventInfo){
		this.eventInfo = eventInfo;
	}

	public String getEventInfo(){
		return eventInfo;
	}

	public void setEventTime(long eventTime){
		this.eventTime = eventTime;
	}

	public long getEventTime(){
		return eventTime;
	}

	public void setHikerId(int hikerId){
		this.hikerId = hikerId;
	}

	public int getHikerId(){
		return hikerId;
	}

	public void setAlertId(int alertId){
		this.alertId = alertId;
	}

	public int getAlertId(){
		return alertId;
	}

	public void setRadius(int radius){
		this.radius = radius;
	}

	public int getRadius(){
		return radius;
	}

	public void setLat(long lat){
		this.lat = lat;
	}

	public long getLat(){
		return lat;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}