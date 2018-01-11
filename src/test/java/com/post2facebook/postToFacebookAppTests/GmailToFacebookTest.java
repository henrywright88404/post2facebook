package com.post2facebook.postToFacebookAppTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import com.post2facebook.Gmail.EmailMessage;
import com.post2facebook.postToFacebookApp.GmailToFacebook;

public class GmailToFacebookTest {
    @Test
	public void ensureDBhasMessages(){
    	
		GmailToFacebook g2f = new GmailToFacebook();

		EmailMessage message = g2f.getNextUnpostedMessage();
		System.out.println(message.getId());
		
		if(message.getId() == null | message.getId().equals("") | message.getId().toString().equals("test")){
			g2f.CheckForNewMessagesAddToDB();
			message = g2f.getNextUnpostedMessage();
		}
		
		
		assertNotNull(message);
		
	}
	@Test
	public void testDBAdd() {
		EmailMessage orMessage = new EmailMessage("test","test");
		GmailToFacebook g2f = new GmailToFacebook();
		
		g2f.addMessageToDB(orMessage);
		
		EmailMessage reMessage = g2f.getMessagefromDB("test");
		
		assertEquals(orMessage, reMessage);
	}

	
	
	@Test
	public void testDBdelete() {
		GmailToFacebook g2f = new GmailToFacebook();
		EmailMessage orMessage = new EmailMessage("test","test");

		g2f.deleteMessageFromDB(orMessage);
		
		EmailMessage delmessage = g2f.getMessagefromDB("test");
		assertNull(delmessage);

	}
	
	

}
