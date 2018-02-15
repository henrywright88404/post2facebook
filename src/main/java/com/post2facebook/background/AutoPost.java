package com.post2facebook.background;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ContextLoaderListener;

import com.post2facebook.postToFacebookApp.GmailToFacebook;

@WebListener
public class AutoPost extends ContextLoaderListener {
	
	private ScheduledExecutorService scheduler;
    private GmailToFacebook g2facebook = new GmailToFacebook();


	@Override
	public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				
				
					System.out.println("BACKGROUND - Checking for messages to add to the database");
				g2facebook.CheckForNewMessagesAddToDB();
					System.out.println("BACKGROUND - Finished checking for messages to add to the database");
					System.out.println("BACKGROUND - Posting next unposted message to facebook");
				g2facebook.PostUnpostedMessageToFacebook();
					System.out.println("BACKGROUND - Finished Posting next unposted message to facebook");
			}
        	
        }, 0, 24, TimeUnit.HOURS);

		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		System.out.println("BACKGROUND - detected server stopping stopping shutting down background processes");
		g2facebook = null;
		scheduler.shutdownNow();
		
		System.out.println("BACKGROUND - SHUTDOWN - processes stopped successfully");

	}


}
