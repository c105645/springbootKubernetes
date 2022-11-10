package com.stackroute.newz.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * Please note that this class is annotated with @Document annotation
 * @Document identifies a domain object to be persisted to MongoDB.
 *  
 */

@Entity
public class NewsSource {

	@Id
	@GeneratedValue
    private Long newssourceId;
    
    private String newssourceName;
    
    private String newssourceDesc;
    
    private String newssourceCreatedBy;
    
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

	public void setNewssourceCreationDate() {
		this.newssourceCreationDate = LocalDate.now();
	}

	@Override
	public String toString() {
		return "Newssource [newssourceId=" + newssourceId + ", newssourceName=" + newssourceName + ", newssourceDesc="
				+ newssourceDesc + ", newssourceCreatedBy=" + newssourceCreatedBy + ", newssourceCreationDate="
				+ newssourceCreationDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(newssourceId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsSource other = (NewsSource) obj;
		return Objects.equals(newssourceId, other.newssourceId);
	}


	
}
