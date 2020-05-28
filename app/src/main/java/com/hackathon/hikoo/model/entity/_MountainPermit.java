package com.hackathon.hikoo.model.entity;

import java.util.List;

import com.hackathon.hikoo.model.domain.HikerInfo;
import com.hackathon.hikoo.model.domain.TrailsItem;
import com.squareup.moshi.Json;

public class _MountainPermit {

	@Json(name = "permitAccepted")
	protected String permitAccepted;

	@Json(name = "hikeStart")
	protected long hikeStart;

	@Json(name = "guideContact2")
	protected String guideContact2;

	@Json(name = "hikerInfo")
	protected HikerInfo hikerInfo;

	@Json(name = "permitId")
	protected int permitId;

	@Json(name = "permitName")
	protected String permitName;

	@Json(name = "guideContact")
	protected String guideContact;

	@Json(name = "hikeEnd")
	protected long hikeEnd;

	@Json(name = "memo")
	protected String memo;

	@Json(name = "trails")
	protected List<TrailsItem> trails;

	@Json(name = "hikeFinished")
	protected boolean hikeFinished;

	@Json(name = "acceptedTime")
	protected long acceptedTime;

	@Json(name = "hikeCancelled")
	protected boolean hikeCancelled;

	@Json(name = "hikerId")
	protected int hikerId;

	@Json(name = "hikeStarted")
	protected boolean hikeStarted;

	@Json(name = "id")
	protected int id;

	@Json(name = "logtime")
	protected long logtime;

	@Json(name = "guideName")
	protected String guideName;

	public void setPermitAccepted(String permitAccepted){
		this.permitAccepted = permitAccepted;
	}

	public String getPermitAccepted(){
		return permitAccepted;
	}

	public void setHikeStart(long hikeStart){
		this.hikeStart = hikeStart;
	}

	public long getHikeStart(){
		return hikeStart;
	}

	public void setGuideContact2(String guideContact2){
		this.guideContact2 = guideContact2;
	}

	public String getGuideContact2(){
		return guideContact2;
	}

	public void setHikerInfo(HikerInfo hikerInfo){
		this.hikerInfo = hikerInfo;
	}

	public HikerInfo getHikerInfo(){
		return hikerInfo;
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

	public void setGuideContact(String guideContact){
		this.guideContact = guideContact;
	}

	public String getGuideContact(){
		return guideContact;
	}

	public void setHikeEnd(long hikeEnd){
		this.hikeEnd = hikeEnd;
	}

	public long getHikeEnd(){
		return hikeEnd;
	}

	public void setMemo(String memo){
		this.memo = memo;
	}

	public String getMemo(){
		return memo;
	}

	public void setTrails(List<TrailsItem> trails){
		this.trails = trails;
	}

	public List<TrailsItem> getTrails(){
		return trails;
	}

	public void setHikeFinished(boolean hikeFinished){
		this.hikeFinished = hikeFinished;
	}

	public boolean isHikeFinished(){
		return hikeFinished;
	}

	public void setAcceptedTime(long acceptedTime){
		this.acceptedTime = acceptedTime;
	}

	public long getAcceptedTime(){
		return acceptedTime;
	}

	public void setHikeCancelled(boolean hikeCancelled){
		this.hikeCancelled = hikeCancelled;
	}

	public boolean isHikeCancelled(){
		return hikeCancelled;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLogtime(long logtime){
		this.logtime = logtime;
	}

	public long getLogtime(){
		return logtime;
	}

	public void setGuideName(String guideName){
		this.guideName = guideName;
	}

	public String getGuideName(){
		return guideName;
	}
}