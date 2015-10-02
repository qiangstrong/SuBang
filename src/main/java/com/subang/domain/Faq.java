package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class Faq implements Filter, Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(max = 100)
	private String question;
	@NotNull
	@Length(max = 1000)
	private String answer;

	public Faq() {
	}

	public Faq(Integer id, String question, String answer) {
		this.id = id;
		this.question = question;
		this.answer = answer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void doFilter(Object object) {
		Faq faq = (Faq) object;
		if (this.id == null) {
			faq.id = null;
		}
		if (this.question == null) {
			faq.question = null;
		}
		if (this.answer == null) {
			faq.answer = null;
		}
	}

}
