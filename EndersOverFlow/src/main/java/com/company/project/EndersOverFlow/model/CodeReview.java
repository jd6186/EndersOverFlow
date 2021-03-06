package com.company.project.EndersOverFlow.model;


import javax.persistence.*;


@Entity(name = "CODE_REVIEW")
public class CodeReview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long CR_NO;
	private String CR_TITLE;
	private Long CR_CREATER;
	private String CR_CONTENTS;
	private String CR_CREATEDAY;
	private String CR_UPDATEDAY;
	private String CR_ISVIEW;
	private Long CR_STAR_COUNT;

	public CodeReview() {
	}

	public CodeReview(Long cR_NO, String cR_TITLE, Long cR_CREATER, String cR_CONTENTS, String cR_CREATEDAY,
			String cR_UPDATEDAY, String cR_ISVIEW, Long cR_STAR_COUNT) {
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

	public Long getCR_CREATER() {
		return CR_CREATER;
	}

	public void setCR_CREATER(Long cR_CREATER) {
		CR_CREATER = cR_CREATER;
	}

	public String getCR_CONTENTS() {
		return CR_CONTENTS;
	}

	public void setCR_CONTENTS(String cR_CONTENTS) {
		CR_CONTENTS = cR_CONTENTS;
	}

	public String getCR_CREATEDAY() {
		return CR_CREATEDAY;
	}

	public void setCR_CREATEDAY(String cR_CREATEDAY) {
		CR_CREATEDAY = cR_CREATEDAY;
	}

	public String getCR_UPDATEDAY() {
		return CR_UPDATEDAY;
	}

	public void setCR_UPDATEDAY(String cR_UPDATEDAY) {
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
