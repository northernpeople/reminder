package com.reminder.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Reminder {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private User user;
	
	@NotNull
	private LocalDateTime remindOn;
	
	@NotNull
	@Size(min=1, max=255)
	private String text;
	
	@NotNull
	@Size(min=1, max=255)
	private String topic;
	
	public Long getId() {
		return id;
	}
	public User getUser() {
		return user;
	}
	public LocalDateTime getRemindOn() {
		return remindOn;
	}
	public String getText() {
		return text;
	}
	public String getTopic() {
		return topic;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setRemindOn(LocalDateTime remindOn) {
		this.remindOn = remindOn;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((remindOn == null) ? 0 : remindOn.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
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
		Reminder other = (Reminder) obj;
		if (remindOn == null) {
			if (other.remindOn != null)
				return false;
		} else if (!remindOn.equals(other.remindOn))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Reminder [id=" + id + ", user=" + user + ", remindOn="
				+ remindOn + ", text=" + text + ", topic=" + topic + "]";
	}
	
	
	
	
	
	

}
