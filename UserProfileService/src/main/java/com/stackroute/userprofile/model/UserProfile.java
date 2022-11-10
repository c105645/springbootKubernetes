package com.stackroute.userprofile.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/*
 * Please note that this class is annotated with @Document annotation
 * @Document identifies a domain object to be persisted to MongoDB.
 *  
 */
@Entity
public class UserProfile {

	/*
	 * This class should have six fields (userId,firstName,
	 * lastName,contact,email,createdAt). Out of these six fields, the field
	 * userId should be annotated with @Id (This annotation explicitly specifies the document
	 * identifier). This class should also contain the getters and setters for the
	 * fields, along with the no-arg , parameterized constructor and toString
	 * method.The value of createdAt should not be accepted from the user but
	 * should be always initialized with the system date.
	 */

    @Id
    
	private String userId;
    
    @NotBlank(message="First Name cannot be Blank")
    private String firstName;
    
    @NotBlank(message="Last Name cannot be Blank")
    private String lastName;
    
    @NotBlank(message="Contact cannot be Blank")
    private String contact;
    
    private LocalDateTime createdAt;
    
    @Email(message = "Email should be valid")
    private String email;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserProfile [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", contact="
				+ contact + ", createdAt=" + createdAt + ", email=" + email + "]";
	}
	
	@Override
	public int hashCode() {
		return 41;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
	if(!(obj instanceof UserProfile)) return false;
		UserProfile other = (UserProfile) obj;
		return userId != null && userId.equals(other.userId);
	}

}
