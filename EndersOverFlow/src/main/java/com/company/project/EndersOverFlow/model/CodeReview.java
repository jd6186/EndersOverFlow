package com.company.project.EndersOverFlow.model;


import java.time.LocalDate;

import javax.persistence.*;


@Entity(name = "CODE_REVIEW")
public class CodeReview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long CR_NO;
	private String CR_TITLE;
	@ManyToOne
	@JoinColumn(name = "ENDERS_MEMBER", referencedColumnName = "MBR_NO")
	private Member CR_CREATER;
	private String CR_CONTENTS;
	private LocalDate CR_CREATEDAY;
	private LocalDate CR_UPDATEDAY;
	private String CR_ISVIEW;
	private Long CR_STAR_COUNT;
	private String CR_TYPE; 			// 글 종류(코드 질문인지 아니면 소개글인지)
	private String CR_COMMENTYN;
	private String CR_QUE_COMMENTYN; 	// 질문글에 댓글이 달리면 이제 글 수정 불가능하게 만들기 위해 필요

	public CodeReview() {
	}

	public CodeReview(String cR_TITLE, String cR_CONTENTS) {
		super();
		CR_TITLE = cR_TITLE;
		CR_CONTENTS = cR_CONTENTS;
	}

	public CodeReview(Long cR_NO, String cR_TITLE, Member cR_CREATER, String cR_CONTENTS, LocalDate cR_CREATEDAY,
			LocalDate cR_UPDATEDAY, String cR_ISVIEW, Long cR_STAR_COUNT) {
		super();
		CR_NO = cR_NO;
		CR_TITLE = cR_TITLE;
		CR_CREATER = cR_CREATER;
		CR_CONTENTS = cR_CONTENTS;
		CR_CREATEDAY = cR_CREATEDAY;
		CR_UPDATEDAY = cR_UPDATEDAY;
		CR_ISVIEW = cR_ISVIEW;
		CR_STAR_COUNT = cR_STAR_COUNT;
	}

	public String getCR_TYPE() {
		return CR_TYPE;
	}

	public void setCR_TYPE(String cR_TYPE) {
		CR_TYPE = cR_TYPE;
	}

	public String getCR_COMMENTYN() {
		return CR_COMMENTYN;
	}

	public void setCR_COMMENTYN(String cR_COMMENTYN) {
		CR_COMMENTYN = cR_COMMENTYN;
	}

	public String getCR_QUE_COMMENTYN() {
		return CR_QUE_COMMENTYN;
	}

	public void setCR_QUE_COMMENTYN(String cR_QUE_COMMENTYN) {
		CR_QUE_COMMENTYN = cR_QUE_COMMENTYN;
	}

	public Long getCR_NO() {
		return CR_NO;
	}

	public void setCR_NO(Long cR_NO) {
		CR_NO = cR_NO;
	}

	public String getCR_TITLE() {
		return CR_TITLE;
	}

	public void setCR_TITLE(String cR_TITLE) {
		CR_TITLE = cR_TITLE;
	}

	public Member getCR_CREATER() {
		return CR_CREATER;
	}

	public void setCR_CREATER(Member cR_CREATER) {
		CR_CREATER = cR_CREATER;
	}

	public String getCR_CONTENTS() {
		return CR_CONTENTS;
	}

	public void setCR_CONTENTS(String cR_CONTENTS) {
		CR_CONTENTS = cR_CONTENTS;
	}

	public LocalDate getCR_CREATEDAY() {
		return CR_CREATEDAY;
	}

	public void setCR_CREATEDAY(LocalDate nowDate) {
		CR_CREATEDAY = nowDate;
	}

	public LocalDate getCR_UPDATEDAY() {
		return CR_UPDATEDAY;
	}

	public void setCR_UPDATEDAY(LocalDate cR_UPDATEDAY) {
		CR_UPDATEDAY = cR_UPDATEDAY;
	}

	public String getCR_ISVIEW() {
		return CR_ISVIEW;
	}

	public void setCR_ISVIEW(String cR_ISVIEW) {
		CR_ISVIEW = cR_ISVIEW;
	}

	public Long getCR_STAR_COUNT() {
		return CR_STAR_COUNT;
	}

	public void setCR_STAR_COUNT(Long cR_STAR_COUNT) {
		CR_STAR_COUNT = cR_STAR_COUNT;
	}

	@Override
	public String toString() {
		return "Member [CR_NO=" + CR_NO + ", CR_TITLE=" + CR_TITLE + ", CR_CREATER=" + CR_CREATER + ", CR_CONTENTS="
				+ CR_CONTENTS + ", CR_CREATEDAY=" + CR_CREATEDAY + ", CR_UPDATEDAY=" + CR_UPDATEDAY + ", CR_ISVIEW="
				+ CR_ISVIEW + ", CR_STAR_COUNT=" + CR_STAR_COUNT + "]";
	}


}
