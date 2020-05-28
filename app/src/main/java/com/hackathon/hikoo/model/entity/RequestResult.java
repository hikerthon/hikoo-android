package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

import java.util.List;

public class RequestResult {

	@Json(name = "success")
	private boolean success;

	@Json(name = "errorMessage")
	private String errorMessage;

	@Json(name = "accessToken")
	private String accessToken;

	@Json(name = "imagePath")
	private String imagePath;


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

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setImagePath(String imagePath){
		this.imagePath = imagePath;
	}

	public String getImagePath(){
		return imagePath;
	}
}