package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class _User {

	@Json(name = "lastName")
	protected String lastName;

	@Json(name = "address")
	protected String address;

	@Json(name = "gender")
	protected String gender;

	@Json(name = "mobileNumber")
	protected String mobileNumber;

	@Json(name = "emergencyContact")
	protected String emergencyContact;

	@Json(name = "userPwd")
	protected String userPwd;

	@Json(name = "idNumber")
	protected String idNumber;

	@Json(name = "emergencyNumber")
	protected String emergencyNumber;

	@Json(name = "userId")
	protected int userId;

	@Json(name = "firstName")
	protected String firstName;

	@Json(name = "nationality")
	protected String nationality;

	@Json(name = "dob")
	protected String dob;

	@Json(name = "satelliteNumber")
	protected String satelliteNumber;

	@Json(name = "serPwd")
	protected String serPwd;

	@Json(name = "email")
	protected String email;

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	public void setEmergencyContact(String emergencyContact){
		this.emergencyContact = emergencyContact;
	}

	public String getEmergencyContact(){
		return emergencyContact;
	}

	public void setUserPwd(String userPwd){
		this.userPwd = userPwd;
	}

	public String getUserPwd(){
		return userPwd;
	}

	public void setIdNumber(String idNumber){
		this.idNumber = idNumber;
	}

	public String getIdNumber(){
		return idNumber;
	}

	public void setEmergencyNumber(String emergencyNumber){
		this.emergencyNumber = emergencyNumber;
	}

	public String getEmergencyNumber(){
		return emergencyNumber;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setNationality(String nationality){
		this.nationality = nationality;
	}

	public String getNationality(){
		return nationality;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setSatelliteNumber(String satelliteNumber){
		this.satelliteNumber = satelliteNumber;
	}

	public String getSatelliteNumber(){
		return satelliteNumber;
	}

	public void setSerPwd(String serPwd){
		this.serPwd = serPwd;
	}

	public String getSerPwd(){
		return serPwd;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}