package com.company.project.EndersOverFlow.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="CODE_REVIEW")
public class Comments {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long CM_NO;
	private Long CR_NO;
	private String CM_CREATER;
	private String CM_CONTENTS;
	private String CM_CREATEDAY;
	private String CM_UPDATEDAY;
	private String CM_ISVIEW;
	private Long CM_STAR_COUNT;

	public Comments() {
	}

	public Comments(Long cM_NO, Long cR_NO, String cM_CREATER, String cM_CONTENTS, String cM_CREATEDAY,
			String cM_UPDATEDAY, String cM_ISVIEW, Long cM_STAR_COUNT) {
		super();
		CM_NO = cM_NO;
		CR_NO = cR_NO;
		CM_CREATER = cM_CREATER;
		CM_CONTENTS = cM_CONTENTS;
		CM_CREATEDAY = cM_CREATEDAY;
		CM_UPDATEDAY = cM_UPDATEDAY;
		CM_ISVIEW = cM_ISVIEW;
		CM_STAR_COUNT = cM_STAR_COUNT;
	}

	
	
	public Long getCM_NO() {
		return CM_NO;
	}

	public void setCM_NO(Long cM_NO) {
		CM_NO = cM_NO;
	}

	public Long getCR_NO() {
		return CR_NO;
	}

	public void setCR_NO(Long cR_NO) {
		CR_NO = cR_NO;
	}

	public String getCM_CREATER() {
		return CM_CREATER;
	}

	public void setCM_CREATER(String cM_CREATER) {
		CM_CREATER = cM_CREATER;
	}

	public String getCM_CONTENTS() {
		return CM_CONTENTS;
	}

	public void setCM_CONTENTS(String cM_CONTENTS) {
		CM_CONTENTS = cM_CONTENTS;
	}

	public String getCM_CREATEDAY() {
		return CM_CREATEDAY;
	}

	public void setCM_CREATEDAY(String cM_CREATEDAY) {
		CM_CREATEDAY = cM_CREATEDAY;
	}

	public String getCM_UPDATEDAY() {
		return CM_UPDATEDAY;
	}

	public void setCM_UPDATEDAY(String cM_UPDATEDAY) {
		CM_UPDATEDAY = cM_UPDATEDAY;
	}

	public String getCM_ISVIEW() {
		return CM_ISVIEW;
	}

	public void setCM_ISVIEW(String cM_ISVIEW) {
		CM_ISVIEW = cM_ISVIEW;
	}

	public Long getCM_STAR_COUNT() {
		return CM_STAR_COUNT;
	}

	public void setCM_STAR_COUNT(Long cM_STAR_COUNT) {
		CM_STAR_COUNT = cM_STAR_COUNT;
	}

	@Override
	public String toString() {
		return "Comments [CM_NO=" + CM_NO + ", CR_NO=" + CR_NO + ", CM_CREATER=" + CM_CREATER + ", CM_CONTENTS="
				+ CM_CONTENTS + ", CM_CREATEDAY=" + CM_CREATEDAY + ", CM_UPDATEDAY=" + CM_UPDATEDAY + ", CM_ISVIEW="
				+ CM_ISVIEW + ", CM_STAR_COUNT=" + CM_STAR_COUNT + "]";
	}
	
}
