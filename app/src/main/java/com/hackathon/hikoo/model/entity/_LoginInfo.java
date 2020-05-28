package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class _LoginInfo {

	@Json(name = "access_token")
	protected String accessToken;

	@Json(name = "refresh_token")
	protected String refreshToken;

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setRefreshToken(String refreshToken){
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken(){
		return refreshToken;
	}
}