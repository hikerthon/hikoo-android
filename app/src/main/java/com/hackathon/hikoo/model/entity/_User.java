package com.hackathon.hikoo.model.entity;

import com.squareup.moshi.Json;

public class _User {

	@Json(name = "lastName")
	protected String lastName;

	@Json(name = "image")
	protected String image;

	@Json(name = "address")
	protected String address;

	@Json(name = "gender")
	protected String gender;

	@Json(name = "mobileNumber")
	protected String mobileNumber;

	@Json(name = "emergencyContact")
	protected String emergencyContact;

	@Json(name = "emergencyNumber")
	protected String emergencyNumber;

	@Json(name = "firstName")
	protected String firstName;

	@Json(name = "password")
	protected String password;

	@Json(name = "identification")
	protected String identification;

	@Json(name = "nationality")
	protected String nationality;

	@Json(name = "dob")
	protected long dob;

	@Json(name = "satelliteNumber")
	protected String satelliteNumber;

	@Json(name = "identificationNumber")
	protected String identificationNumber;

	@Json(name = "id")
	protected int id;

	@Json(name = "homeNumber")
	protected String homeNumber;

	@Json(name = "fcmToken")
	protected String fcmToken;

	@Json(name = "email")
	protected String email;

	@Json(name = "username")
	protected String username;

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
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

	public void setEmergencyNumber(String emergencyNumber){
		this.emergencyNumber = emergencyNumber;
	}

	public String getEmergencyNumber(){
		return emergencyNumber;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setIdentification(String identification){
		this.identification = identification;
	}

	public String getIdentification(){
		return identification;
	}

	public void setNationality(String nationality){
		this.nationality = nationality;
	}

	public String getNationality(){
		return nationality;
	}

	public void setDob(long dob){
		this.dob = dob;
	}

	public long getDob(){
		return dob;
	}

	public void setSatelliteNumber(String satelliteNumber){
		this.satelliteNumber = satelliteNumber;
	}

	public String getSatelliteNumber(){
		return satelliteNumber;
	}

	public void setIdentificationNumber(String identificationNumber){
		this.identificationNumber = identificationNumber;
	}

	public String getIdentificationNumber(){
		return identificationNumber;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setHomeNumber(String homeNumber){
		this.homeNumber = homeNumber;
	}

	public String getHomeNumber(){
		return homeNumber;
	}

	public void setFcmToken(String fcmToken){
		this.fcmToken = fcmToken;
	}

	public String getFcmToken(){
		return fcmToken;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}