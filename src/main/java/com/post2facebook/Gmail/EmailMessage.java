package com.post2facebook.Gmail;

import javax.persistence.*;

@Entity
@Table (name = "messages_posted")
public class EmailMessage {

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="message")
	private String message;
	
	@Column(name="been_posted")
	private boolean beenPosted;
	
	
	public boolean hasBeenPosted() {
		return beenPosted;
	}
	public boolean getBeenPosted() {
		return beenPosted;
	}
	
	public void setBeenPosted(boolean beenPosted) {
		this.beenPosted = beenPosted;
	}
	
	public EmailMessage(){
		
	}
	
	public EmailMessage(String id, String message) {
		super();
		this.id = id;
		this.message = message;
		this.beenPosted = false;
	}
	
	public EmailMessage(String id, String message,boolean posted) {
		super();
		this.id = id;
		this.message = message;
		this.beenPosted = posted;
	}
	
	public String getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (beenPosted ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		EmailMessage other = (EmailMessage) obj;
		if (beenPosted != other.beenPosted)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}


}
