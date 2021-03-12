package com.company.project.EndersOverFlow.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity(name = "ENDERS_MEMBER")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long MBR_NO;
	private String MBR_EMAIL;
	private String MBR_PASSWORD;
	private String MBR_NICNAME;
	private String MBR_AUTH;
	private LocalDate MBR_SIGNUP_DATE;
	private LocalDate MBR_PASSWORD_UPDATE_DATE;
	private String MBR_LOGINUUID;
	

	public Member() {
	}

	public Member(Long mBR_NO, String mBR_EMAIL, String mBR_PASSWORD, String mBR_NICNAME, String mBR_AUTH,
			LocalDate mBR_SIGNUP_DATE, LocalDate mBR_PASSWORD_UPDATE_DATE) {
		super();
		MBR_NO = mBR_NO;
		MBR_EMAIL = mBR_EMAIL;
		MBR_PASSWORD = mBR_PASSWORD;
		MBR_NICNAME = mBR_NICNAME;
		MBR_AUTH = mBR_AUTH;
		MBR_SIGNUP_DATE = mBR_SIGNUP_DATE;
		MBR_PASSWORD_UPDATE_DATE = mBR_PASSWORD_UPDATE_DATE;
	}
	

	public Member(String mBR_EMAIL, String mBR_PASSWORD, String mBR_NICNAME, String mBR_AUTH, LocalDate mBR_SIGNUP_DATE,
			LocalDate mBR_PASSWORD_UPDATE_DATE, String mBR_LOGINUUID) {
		super();
		MBR_EMAIL = mBR_EMAIL;
		MBR_PASSWORD = mBR_PASSWORD;
		MBR_NICNAME = mBR_NICNAME;
		MBR_AUTH = mBR_AUTH;
		MBR_SIGNUP_DATE = mBR_SIGNUP_DATE;
		MBR_PASSWORD_UPDATE_DATE = mBR_PASSWORD_UPDATE_DATE;
		MBR_LOGINUUID = mBR_LOGINUUID;
	}

	public Member(Long mBR_NO, String mBR_EMAIL, String mBR_PASSWORD, String mBR_NICNAME, String mBR_AUTH) {
		super();
		MBR_NO = mBR_NO;
		MBR_EMAIL = mBR_EMAIL;
		MBR_NICNAME = mBR_NICNAME;
		MBR_PASSWORD = mBR_PASSWORD;
		MBR_AUTH = mBR_AUTH;
	}

	public Long getMBR_NO() {
		return MBR_NO;
	}

	public void setMBR_NO(Long mBR_NO) {
		MBR_NO = mBR_NO;
	}

	public String getMBR_EMAIL() {
		return MBR_EMAIL;
	}

	public void setMBR_EMAIL(String mBR_EMAIL) {
		MBR_EMAIL = mBR_EMAIL;
	}

	public String getMBR_PASSWORD() {
		return MBR_PASSWORD;
	}

	public void setMBR_PASSWORD(String mBR_PASSWORD) {
		MBR_PASSWORD = mBR_PASSWORD;
	}

	public String getMBR_NICNAME() {
		return MBR_NICNAME;
	}

	public void setMBR_NICNAME(String mBR_NICNAME) {
		MBR_NICNAME = mBR_NICNAME;
	}

	public String getMBR_AUTH() {
		return MBR_AUTH;
	}

	public void setMBR_AUTH(String mBR_AUTH) {
		MBR_AUTH = mBR_AUTH;
	}

	public LocalDate getMBR_SIGNUP_DATE() {
		return MBR_SIGNUP_DATE;
	}

	public void setMBR_SIGNUP_DATE(LocalDate mBR_SIGNUP_DATE) {
		MBR_SIGNUP_DATE = mBR_SIGNUP_DATE;
	}

	public LocalDate getMBR_PASSWORD_UPDATE_DATE() {
		return MBR_PASSWORD_UPDATE_DATE;
	}

	public void setMBR_PASSWORD_UPDATE_DATE(LocalDate mBR_PASSWORD_UPDATE_DATE) {
		MBR_PASSWORD_UPDATE_DATE = mBR_PASSWORD_UPDATE_DATE;
	}

	public String getMBR_LOGINUUID() {
		return MBR_LOGINUUID;
	}

	public void setMBR_LOGINUUID(String mBR_LOGINUUID) {
		MBR_LOGINUUID = mBR_LOGINUUID;
	}

	@Override
	public String toString() {
		return "Member [MBR_NO=" + MBR_NO + ", MBR_EMAIL=" + MBR_EMAIL + ", MBR_PASSWORD=" + MBR_PASSWORD
				+ ", MBR_NICNAME=" + MBR_NICNAME + ", MBR_AUTH=" + MBR_AUTH + ", MBR_SIGNUP_DATE=" + MBR_SIGNUP_DATE
				+ ", MBR_PASSWORD_UPDATE_DATE=" + MBR_PASSWORD_UPDATE_DATE + ", MBR_LOGINUUID=" + MBR_LOGINUUID + "]";
	}

}
