package com.post2facebook.postToFacebookApp;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.post2facebook.Gmail.EmailMessage;
import com.post2facebook.Gmail.GmailController;
import com.post2facebook.facebook.FacebookPost;

public class GmailToFacebook {
	
	private SessionFactory getSessionFactory(){
		
		Resource resource = new ClassPathResource("dbConnection.properties");
		
		 Properties dbConnectionProperties = new Properties();
		 
		 try {
			dbConnectionProperties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 SessionFactory factory = new Configuration()
					.configure("hibernate.cfg.xml")
					.mergeProperties(dbConnectionProperties)
					.addAnnotatedClass(EmailMessage.class)
					.buildSessionFactory();
		System.out.println(factory.getProperties().toString());
		return factory;
		
	}

	public void CheckForNewMessagesAddToDB(){
		// create session factory 
		SessionFactory factory = getSessionFactory();
		
		// create a session 
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		try {
			List<String> messageIdList = GmailController.getAllMessageIdMatchingQuery("darby@luv2code.com", "P.S. Do you know someone that could use a little help learning more about Java? Please forward this email to them. They'll thank you for it :-)");
			int count = 0;
			for(String messageID : messageIdList){
				
				EmailMessage emailFromDB = session.get(EmailMessage.class, messageID);
				if(emailFromDB !=null){
					System.out.println("Message from DB "+ emailFromDB.getId());
				}else{
					
					session.save(GmailController.getMessage(messageID));
					System.out.println("Emails Missing from DB ="+ ++count);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			factory.close();
		}
		
	}
	
	public void PostUnpostedMessageToFacebook(){
	
		
		SessionFactory factory = getSessionFactory();
		
		// create a session 
		Session session = factory.getCurrentSession();
	
		session.beginTransaction();
		
		try{
			List<EmailMessage> messageList = session.createCriteria(EmailMessage.class).list();
			
			for(EmailMessage message: messageList){
				if(message.hasBeenPosted() == false){
					FacebookPost fbPost = new FacebookPost();
					fbPost.createTextPostInGroup(message.getMessage(), fbPost.getJava101id());
					System.out.println("has been posted before = "+message.hasBeenPosted());
					message.setBeenPosted(true);
					System.out.println("has been posted after = "+message.hasBeenPosted());
					session.update(message);
					session.getTransaction().commit();
					break;
				}
			}
			
		}finally{
			session.close();
		}
	}
	
	public EmailMessage getNextUnpostedMessage(){
		SessionFactory factory = getSessionFactory();
		
		// create a session 
				Session session = factory.getCurrentSession();
				session.beginTransaction();
				
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<EmailMessage> criteria = builder.createQuery(EmailMessage.class);
				Root<EmailMessage> emailMessageRoot = criteria.from(EmailMessage.class);
				criteria.select(emailMessageRoot);
				
				criteria.where(builder.equal(emailMessageRoot.get("beenPosted"),false));
				
				List<EmailMessage> messageList = session.createQuery(criteria).getResultList();
				
				return messageList.get(0);
	}
}
