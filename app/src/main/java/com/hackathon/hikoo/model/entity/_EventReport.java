package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

import java.util.List;

public class _EventReport {

	@Json(name = "eventTypeId")
	protected int eventTypeId;

	@Json(name = "eventInfo")
	protected String eventInfo;

	@Json(name = "stat")
	protected String stat;

	@Json(name = "alertLevelId")
	protected int alertLevelId;

	@Json(name = "hikeId")
	protected int hikeId;

	@Json(name = "eventTime")
	protected long eventTime;

	@Json(name = "reporterId")
	protected int reporterId;

	@Json(name = "latpt")
	protected double latpt;

	@Json(name = "lngpt")
	protected double lngpt;

	@Json(name = "radius")
	protected int radius;

	@Json(name = "attachments")
	protected List<String> attachments;

	public int getEventTypeId(){
		return eventTypeId;
	}

	public void setEventTypeId(int eventTypeId){
		this.eventTypeId = eventTypeId;
	}

	public String getEventInfo(){
		return eventInfo;
	}

	public void setEventInfo(String eventInfo){
		this.eventInfo = eventInfo;
	}

	public String getStat(){
		return stat;
	}

	public void setStat(String stat){
		this.stat = stat;
	}

	public int getAlertLevelId(){
		return alertLevelId;
	}

	public void setAlertLevelId(int alertLevelId){
		this.alertLevelId = alertLevelId;
	}

	public int getHikeId(){
		return hikeId;
	}

	public void setHikeId(int hikeId){
		this.hikeId = hikeId;
	}

	public long getEventTime(){
		return eventTime;
	}

	public void setEventTime(long eventTime){
		this.eventTime = eventTime;
	}

	public int getReporterId(){
		return reporterId;
	}

	public void setReporterId(int reporterId){
		this.reporterId = reporterId;
	}

	public double getLatpt(){
		return latpt;
	}

	public void setLatpt(double latpt){
		this.latpt = latpt;
	}

	public double getLngpt(){
		return lngpt;
	}

	public void setLngpt(double lngpt){
		this.lngpt = lngpt;
	}

	public int getRadius(){
		return radius;
	}

	public void setRadius(int radius){
		this.radius = radius;
	}

	public void setAttachments(List<String> attachments){
		this.attachments = attachments;
	}

	public List<String> getAttachments(){
		return attachments;
	}
}