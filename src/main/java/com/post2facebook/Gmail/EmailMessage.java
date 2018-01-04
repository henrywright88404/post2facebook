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


}
