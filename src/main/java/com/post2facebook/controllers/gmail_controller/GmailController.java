package com.post2facebook.controllers.gmail_controller;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import com.post2facebook.Gmail.EmailMessage;
import com.google.api.services.gmail.Gmail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GmailController {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Gmail API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/gmail-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(GmailScopes.GMAIL_LABELS,GmailScopes.GMAIL_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            GmailController.class.getResourceAsStream("/client_secret_477694311220-cpccia8vgdof4i53acfioictb2l8jih1.apps.googleusercontent.com.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    /**
     * Get Message with given ID.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param messageId ID of Message to retrieve.
     * @return Message Retrieved Message.
     * @throws IOException
     */
    public static Message getMessage(Gmail service, String userId, String messageId)
        throws IOException {
      Message message = service.users().messages().get(userId, messageId).execute();

      System.out.println("Message snippet: " + message.getSnippet());

      return message;
    }

    public static List<Message> listMessagesMatchingQuery(Gmail service, String userId,
    	      String query) throws IOException {
    	    ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();

    	    List<Message> messages = new ArrayList<Message>();
    	    while (response.getMessages() != null) {
    	      messages.addAll(response.getMessages());
    	      if (response.getNextPageToken() != null) {
    	        String pageToken = response.getNextPageToken();
    	        response = service.users().messages().list(userId).setQ(query)
    	            .setPageToken(pageToken).execute();
    	      } else {
    	        break;
    	      }
    	    }
    	    return messages;
    	  }
    
    public static void printAllMessagesFromEmailAddress(String fromEmail) throws IOException {
        // Build a new authorized API client service.
        Gmail service = getGmailService();

        String user = "me";//"me" is a special charecter.

        // Get List of Messages that are from parameter string 
        List<Message>messages= listMessagesMatchingQuery(service, user, "from:"+fromEmail);
        
        if(messages.size() == 0 ){
        	System.out.println("No Messages");
        }else{
        	for(Message message: messages){
        		System.out.println("Printing message with ID: " +message.getId() + "\n\n");
        		Message actualMessage = service.users().messages().get(user, message.getId()).execute();
        		List<MessagePart> part = actualMessage.getPayload().getParts();
        		try{
        			System.out.printf("- %s\n", StringUtils.newStringUtf8(Base64.decodeBase64(part.get(0).getBody().getData())));
        		} catch (NullPointerException n){
        			System.out.print("Null");
        			continue;
        		}
        	}
        	System.out.println("Messages size: "+ messages.size());
        }
       
    }
    
    public static List<EmailMessage> getAllMessagesFromEmailAddressContaining(String fromEmail, String containing) throws IOException {
        // Build a new authorized API client service.
        Gmail service = getGmailService();

        String user = "me";//"me" is a special character.

        // Get List of Messages that are from parameter string 
        List<Message>messages= listMessagesMatchingQuery(service, user, containing + " from:"+fromEmail);
        List<EmailMessage> msgList = new ArrayList<>();
        if(messages.size() == 0 ){
        	System.out.println("No Messages");
        }else{
        	
        	for(Message message: messages){
        		System.out.println("Printing message with ID: " +message.getId() + "\n\n");
        		Message actualMessage = service.users().messages().get(user, message.getId()).execute();
        		List<MessagePart> part = actualMessage.getPayload().getParts();
        		try{
        			String msg = StringUtils
        					.newStringUtf8(Base64.decodeBase64(part
        							.get(0)
        							.getBody()
        							.getData()));
        			
        			msg = msg.replaceAll("Hi Marc", "Hi Java 101");
        			msg = msg.replaceAll("Hey Marc", "Hi Java 101");
        			msg = msg.substring(0, msg.indexOf("34 E Germantown Pike"));
        			msgList.add(new EmailMessage(actualMessage.getId(),msg,false));
        			System.out.printf("- %s\n", msg);
        		} catch (NullPointerException n){
        			System.out.print("Null");
        			continue;
        		}
        	}
        	System.out.println("Messages size: "+ messages.size());
        	
        	
        }
        return msgList;
       
    }
    
    public static Message getMessage(String id){
    	
    	String user = "me";
    	
    	Message message = null;
    	
    	try {
    		Gmail service = getGmailService();
			message = service.users().messages().get(user,id).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		return message;
    	
    }
    
    public static List<String> getAllMessageIdMatchingQuery(String fromEmail, String containing) throws IOException{
    	  // Build a new authorized API client service.
        Gmail service = getGmailService();

        String user = "me";//"me" is a special character.

        // Get List of Messages that are from parameter string 
        List<Message>messages= listMessagesMatchingQuery(service, user, containing + " from:"+fromEmail);
        List<String> msgIdList = new ArrayList<>();
        if(messages.size() == 0 ){
        	System.out.println("No Messages");
        }else{
        	
        	for(Message message: messages){
        		msgIdList.add(message.getId());
        	}
        	System.out.println("Messages id's returned: "+ msgIdList.size());
        	
        	
        }
		return msgIdList;
    	
    }
    
    public static void printMessageFrom(int i, String fromEmail) throws IOException{
    	Gmail service = getGmailService();
    	
    	String user = "me";
        // Get List of Messages that are from parameter string 
        List<Message>messages= listMessagesMatchingQuery(service, user, "from:"+fromEmail);
        
        System.out.println("Messages size: "+ messages.size() + " retriving message "+ i + " from list");
        
        try{
        	Message message = service.users().messages().get(user, messages.get(i).getId()).execute();
        	List<MessagePart> part = message.getPayload().getParts();
        	System.out.printf("- %s\n", StringUtils.newStringUtf8(Base64.decodeBase64(part.get(0).getBody().getData())));
        	        	
        }catch (Exception e){  
        	System.out.println("There are "+ messages.size() +" messages in the list.\n Select a message between 0 and "+ messages.size());
        }
    	
    }

}
