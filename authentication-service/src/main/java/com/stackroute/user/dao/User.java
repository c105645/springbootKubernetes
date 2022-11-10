package com.stackroute.user.dao;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/*
 * The class "User" will be acting as the data model for the User Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */

@Entity
public class User {
	
    /*
	 * This class should have three fields (userId,password,cpassword. Out of these 
	 * five fields, the field userId should be the primary key. This class should
	 * also contain the getters and setters for the fields, along with the no-arg,
	 * parameterized constructor and toString method.
	 */

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message="username cannot be Blank")
    @Column(unique=true)
	private String username;
    @NotBlank(message="Password cannot be Blank")
	private String password;
	private String cpassword;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
    
    public User(){}

    public User(String username, String password,Set<String>roles){
        this.username=username;
        this.password=password;
        this.roles=roles;
    }
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCpassword() {
		return cpassword;
	}


	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	
	
	public Set<String> getRoles() {
		return roles;
	}


	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", cpassword=" + cpassword
				+ ", roles=" + roles + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}



	
}
