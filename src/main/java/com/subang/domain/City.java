package com.subang.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.subang.domain.face.Filter;

public class City implements Filter, Serializable {

	public static String scopePath = "image/info/scope/";

	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Length(min = 1, max = 10)
	private String name;
	@Length(max = 100)
	private String scope;
	@Length(max = 100)
	private String scopeText;

	public City() {
	}

	public City(Integer id, String name, String scope, String scopeText) {
		super();
		this.id = id;
		this.name = name;
		this.scope = scope;
		this.scopeText = scopeText;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void calcScope(String scope) {
		this.scope = scopePath + scope;
	}

	public String getScopeText() {
		return scopeText;
	}

	public void setScopeText(String scopeText) {
		this.scopeText = scopeText;
	}

	public void doFilter(Object object) {
		City city = (City) object;
		if (this.id == null) {
			city.setId(null);
		}
		if (this.name == null) {
			city.setName(null);
		}
		if (this.scope == null) {
			city.setScope(null);
		}
		if (this.scopeText == null) {
			city.setScopeText(null);
		}
	}

}
