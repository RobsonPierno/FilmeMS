package com.streaming.filme.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GenderDTO {

	private Integer id;
	private String name;
	
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

	@JsonIgnore
	public boolean isMadatoryFieldsNotNull() {
		return this.getName() != null;
	}
}
