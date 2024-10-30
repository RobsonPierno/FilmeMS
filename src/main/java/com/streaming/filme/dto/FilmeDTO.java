package com.streaming.filme.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FilmeDTO {
	
	private Integer id;
	private String name;
	private LocalDate dtRelease;
	private String gender;
	private String director;
	private LocalDateTime dtInsert;
	private Boolean active;
	private Integer idPessoa;
	
	@JsonIgnore
	private Date dtReleaseTmp;
	
	@JsonIgnore
	private Date dtInsertTmp;

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Date getDtReleaseTmp() {
		if (this.dtReleaseTmp == null && this.dtRelease != null) {
			this.dtReleaseTmp = Date.from(this.dtRelease
											.atStartOfDay(ZoneId.systemDefault())
											.toInstant());
		}
		return dtReleaseTmp;
	}

	public void setDtReleaseTmp(Date dtReleaseTmp) {
		this.dtReleaseTmp = dtReleaseTmp;
	}

	public Date getDtInsertTmp() {
		if (this.dtInsertTmp == null && this.dtInsert != null) {
			this.dtInsertTmp = Date.from(this.dtInsert
											.atZone(ZoneId.systemDefault())
											.toInstant());
		}
		return dtInsertTmp;
	}

	public void setDtInsertTmp(Date dtInsertTmp) {
		this.dtInsertTmp = dtInsertTmp;
	}

	public void setDtRelease(LocalDate dtRelease) {
		this.dtRelease = dtRelease;
	}

	public void setDtInsert(LocalDateTime dtInsert) {
		this.dtInsert = dtInsert;
	}

	public LocalDate getDtRelease() {
		if (this.dtRelease == null && this.dtReleaseTmp != null) {
			this.dtRelease = Instant.ofEpochMilli(this.dtReleaseTmp.getTime())
									.atZone(ZoneId.systemDefault())
									.toLocalDate();
		}
		return dtRelease;
	}

	public LocalDateTime getDtInsert() {
		if (this.dtInsert == null && this.dtInsertTmp != null) {
			this.dtInsert = Instant.ofEpochMilli(this.dtInsertTmp.getTime())
									.atZone(ZoneId.systemDefault())
									.toLocalDateTime();
		}
		return dtInsert;
	}

	@JsonIgnore
	public boolean isMadatoryFieldsNotNull() {
		return this.getName() != null && this.getDtRelease() != null && this.getGender() != null && this.getDirector() != null && 
				this.getActive() != null && this.getIdPessoa() != null;
	}
}
