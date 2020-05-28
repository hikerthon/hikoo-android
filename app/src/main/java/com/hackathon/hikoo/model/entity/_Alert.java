package com.hackathon.hikoo.model.entity;

import java.util.List;
import com.squareup.moshi.Json;

public class _Alert {

	@Json(name = "attachments")
	protected List<String> attachments;

	@Json(name = "alertLevelId")
	protected int alertLevelId;

	@Json(name = "permitId")
	protected int permitId;

	@Json(name = "permitName")
	protected String permitName;

	@Json(name = "creatorId")
	protected int creatorId;

	@Json(name = "creatorName")
	protected String creatorName;

	@Json(name = "lngpt")
	protected double lngpt;

	@Json(name = "alertLevelName")
	protected String alertLevelName;

	@Json(name = "eventTypeId")
	protected int eventTypeId;

	@Json(name = "eventInfo")
	protected String eventInfo;

	@Json(name = "eventEnd")
	protected String eventEnd;

	@Json(name = "eventTime")
	protected long eventTime;

	@Json(name = "eventTypeName")
	protected String eventTypeName;

	@Json(name = "id")
	protected int id;

	@Json(name = "latpt")
	protected double latpt;

	@Json(name = "radius")
	protected int radius;

	public void setAttachments(List<String> attachments){
		this.attachments = attachments;
	}

	public List<String> getAttachments(){
		return attachments;
	}

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

	public void setPermitName(String permitName){
		this.permitName = permitName;
	}

	public String getPermitName(){
		return permitName;
	}

	public void setCreatorId(int creatorId){
		this.creatorId = creatorId;
	}

	public int getCreatorId(){
		return creatorId;
	}

	public void setCreatorName(String creatorName){
		this.creatorName = creatorName;
	}

	public String getCreatorName(){
		return creatorName;
	}

	public void setLngpt(double lngpt){
		this.lngpt = lngpt;
	}

	public double getLngpt(){
		return lngpt;
	}

	public void setAlertLevelName(String alertLevelName){
		this.alertLevelName = alertLevelName;
	}

	public String getAlertLevelName(){
		return alertLevelName;
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

	public void setEventTime(long eventTime){
		this.eventTime = eventTime;
	}

	public long getEventTime(){
		return eventTime;
	}

	public void setEventTypeName(String eventTypeName){
		this.eventTypeName = eventTypeName;
	}

	public String getEventTypeName(){
		return eventTypeName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLatpt(double latpt){
		this.latpt = latpt;
	}

	public double getLatpt(){
		return latpt;
	}

	public void setRadius(int radius){
		this.radius = radius;
	}

	public int getRadius(){
		return radius;
	}
}