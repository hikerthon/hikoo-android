package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class RequestResult {

	@Json(name = "success")
	private boolean success;

	@Json(name = "errorMessage")
	private String errorMessage;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setErrorMessage(String errorMessage){
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage(){
		return errorMessage;
	}
}