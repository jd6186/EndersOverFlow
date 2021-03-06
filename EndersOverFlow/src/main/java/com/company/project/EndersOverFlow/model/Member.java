package com.company.project.EndersOverFlow.model;

import javax.persistence.*;

@Entity(name = "ENDERS_MEMBER")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long MBR_NO;
	private String MBR_ID;
	private String MBR_NAME;

	public Member() {
	}

	public Member(String id, String name) {
		this.MBR_ID = id;
		this.MBR_NAME = name;
	}

	public Member(Long no, String id, String name) {
		this.MBR_NO = no;
		this.MBR_ID = id;
		this.MBR_NAME = name;
	}

	public Long getMbrNo() {
		return MBR_NO;
	}

	public void setMbrNo(Long mbrNo) {
		this.MBR_NO = mbrNo;
	}

	public String getId() {
		return MBR_ID;
	}

	public void setId(String id) {
		this.MBR_ID = id;
	}

	public String getName() {
		return MBR_NAME;
	}

	public void setName(String name) {
		this.MBR_NAME = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((MBR_ID == null) ? 0 : MBR_ID.hashCode());
		result = prime * result + ((MBR_NO == null) ? 0 : MBR_NO.hashCode());
		result = prime * result + ((MBR_NAME == null) ? 0 : MBR_NAME.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (MBR_ID == null) {
			if (other.MBR_ID != null)
				return false;
		} else if (!MBR_ID.equals(other.MBR_ID))
			return false;
		if (MBR_NO == null) {
			if (other.MBR_NO != null)
				return false;
		} else if (!MBR_NO.equals(other.MBR_NO))
			return false;
		if (MBR_NAME == null) {
			if (other.MBR_NAME != null)
				return false;
		} else if (!MBR_NAME.equals(other.MBR_NAME))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Member [MBR_NO=" + MBR_NO + ", MBR_ID=" + MBR_ID + ", MBR_NAME=" + MBR_NAME + "]";
	}

}
