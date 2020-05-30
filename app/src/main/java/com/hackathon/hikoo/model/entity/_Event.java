package com.hackathon.hikoo.model.entity;

import java.util.List;
import com.squareup.moshi.Json;

public class _Event {

	@Json(name = "stat")
	protected String stat;

	@Json(name = "attachments")
	protected List<String> attachments;

	@Json(name = "alertLevelId")
	protected int alertLevelId;

	@Json(name = "hikeId")
	protected int hikeId;

	@Json(name = "lngpt")
	protected double lngpt;

	@Json(name = "reporterName")
	protected String reporterName;

	@Json(name = "eventTypeId")
	protected int eventTypeId;

	@Json(name = "eventInfo")
	protected String eventInfo;

	@Json(name = "eventTime")
	protected long eventTime;

	@Json(name = "eventTypeName")
	protected String eventTypeName;

	@Json(name = "reporterId")
	protected int reporterId;

	@Json(name = "id")
	protected int id;

	@Json(name = "latpt")
	protected double latpt;

	@Json(name = "radius")
	protected int radius;

	@Json(name = "logtime")
	protected long logtime;

	public void setStat(String stat){
		this.stat = stat;
	}

	public String getStat(){
		return stat;
	}

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

	public void setHikeId(int hikeId){
		this.hikeId = hikeId;
	}

	public int getHikeId(){
		return hikeId;
	}

	public void setLngpt(double lngpt){
		this.lngpt = lngpt;
	}

	public double getLngpt(){
		return lngpt;
	}

	public void setReporterName(String reporterName){
		this.reporterName = reporterName;
	}

	public String getReporterName(){
		return reporterName;
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

	public void setReporterId(int reporterId){
		this.reporterId = reporterId;
	}

	public int getReporterId(){
		return reporterId;
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

	public void setLogtime(long logtime){
		this.logtime = logtime;
	}

	public long getLogtime(){
		return logtime;
	}
}