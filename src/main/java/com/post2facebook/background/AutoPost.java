package com.post2facebook.background;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ContextLoaderListener;

import com.post2facebook.controllers.gmail_controller.GmailToFacebookController;

@WebListener
public class AutoPost extends ContextLoaderListener {
	
	private ScheduledExecutorService scheduler;
    private GmailToFacebookController g2facebook = new GmailToFacebookController();
    private static TimeUnit timeunit = TimeUnit.HOURS; 
    private static int unit = 24;
    private static LocalDateTime timeLastPosted;
    private static LocalDateTime timeNextPost;
    private static Long timeUnit = timeunit.convert(unit, timeunit);
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    

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
					timeLastPosted = LocalDateTime.now();
					
					setTimeNextPost();
					
					System.out.println("Time until next post :"+timeUnit 
							+ " " + timeunit.toString());
					System.out.println("Next post will be at: "+ getTimeNextPost());
					
			}
        	
        }, 0, unit, timeunit);

		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		System.out.println("BACKGROUND - detected server stopping stopping shutting down background processes");
		g2facebook = null;
		try {
			while(!scheduler.isShutdown()){
				scheduler.shutdownNow();
				System.out.println("BACKGROUND - SHUTDOWN - processes shutting down");
				
					Thread.sleep(5000L);
				} 
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("BACKGROUND - SHUTDOWN - processes stopped successfully");
		
	}

	public static TimeUnit getTimeunit() {
		return timeunit;
	}

	public static void setTimeunit(TimeUnit timeunit) {
		AutoPost.timeunit = timeunit;
	}

	public static int getUnit() {
		return unit;
	}

	public static void setUnit(int unit) {
		AutoPost.unit = unit;
	}

	public static LocalDateTime getTimeLastPosted() {
		return timeLastPosted;
	}

	public static void setTimeLastPosted(LocalDateTime timeLastPosted) {
		AutoPost.timeLastPosted = timeLastPosted;
	}

	public static String getTimeNextPost() {
		return timeNextPost.format(dtf);
	}

	public static void setTimeNextPost() {
		
		switch (timeunit) {
		case DAYS:
			timeNextPost = timeLastPosted.plusDays(getUnit());
			break;
		case HOURS:
			timeNextPost = timeLastPosted.plusHours(getUnit());
			break;
		case MINUTES:
			timeNextPost = timeLastPosted.plusMinutes(getUnit());
			break;

		default:
			break;
		}
	}


}
