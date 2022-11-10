package com.stackroute.newz.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class NewssourceDto {
	
	private Long newssourceId;
    
	@NotBlank(message="Source name cannot be blank")
    private String newssourceName;
    
    private String newssourceDesc;
    
    private String newssourceCreatedBy;
    
	@JsonSerialize(using = ToStringSerializer.class)
    private LocalDate newssourceCreationDate;

	public Long getNewssourceId() {
		return newssourceId;
	}

	public void setNewssourceId(Long newssourceId) {
		this.newssourceId = newssourceId;
	}

	public String getNewssourceName() {
		return newssourceName;
	}

	public void setNewssourceName(String newssourceName) {
		this.newssourceName = newssourceName;
	}

	public String getNewssourceDesc() {
		return newssourceDesc;
	}

	public void setNewssourceDesc(String newssourceDesc) {
		this.newssourceDesc = newssourceDesc;
	}

	public String getNewssourceCreatedBy() {
		return newssourceCreatedBy;
	}

	public void setNewssourceCreatedBy(String newssourceCreatedBy) {
		this.newssourceCreatedBy = newssourceCreatedBy;
	}

	public LocalDate getNewssourceCreationDate() {
		return newssourceCreationDate;
	}

	public void setNewssourceCreationDate(LocalDate newssourceCreationDate) {
		this.newssourceCreationDate = newssourceCreationDate;
	}

	@Override
	public String toString() {
		return "NewssourceDto [newssourceId=" + newssourceId + ", newssourceName=" + newssourceName
				+ ", newssourceDesc=" + newssourceDesc + ", newssourceCreatedBy=" + newssourceCreatedBy
				+ ", newssourceCreationDate=" + newssourceCreationDate + "]";
	}

    
}
