package com.reminder.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Entity
public class User {

	@NotNull
	@Size(min=5, max=255)
	@Pattern(regexp = "(\\d|\\w|\\.)+\\@(\\d|\\w|\\.)+\\.(\\d|\\w)+",  message = "example: john.berg@gmail.com")
	private String email;
	
	@NotNull
	@Size(min=7, max=255)
	private String password;

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private Set<Reminder> reminders = new HashSet<>();

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Long getId() {
		return id;
	}

	public Set<Reminder> getReminders() {
		return reminders;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setReminders(Set<Reminder> reminders) {
		this.reminders = reminders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public void link(Reminder r){
		this.getReminders().add(r);
		r.setUser(this);
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", id=" + id
				+ "]";
	}
	
	



	
	

}
