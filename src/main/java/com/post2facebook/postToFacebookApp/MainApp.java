package com.post2facebook.postToFacebookApp;

import static org.junit.Assert.assertNotNull;

import com.post2facebook.Gmail.EmailMessage;
import com.post2facebook.chartCreation.*;
import com.post2facebook.claim_data.ClaimDataSummarizer;
import com.post2facebook.claim_data.ExcelReader;
import com.post2facebook.facebook.FacebookPost;
import com.post2facebook.viewController.GmailToFacebookContoller;

public class MainApp {

	public static void main(String[] args) {
		ensureDBhasMessages();
	}
	
	public static void ensureDBhasMessages(){
    	
		GmailToFacebook g2f = new GmailToFacebook();

		EmailMessage message = g2f.getNextUnpostedMessage();
		System.out.println(message.getId());
		
		if(message.getId() == null || message.getId().equals("") || message.getId().equals("999999999")){
			g2f.CheckForNewMessagesAddToDB();
			message = g2f.getNextUnpostedMessage();
		}		
	}
	
	public static void gmailToFacebookOnlyDemo(){
		GmailToFacebook gmailToFacebook = new GmailToFacebook();
		gmailToFacebook.PostUnpostedMessageToFacebook();
	}
	
	public static void excelReportSummaryOnly(){
		ExcelReader reader = new ExcelReader();
		ClaimDataSummarizer summarize = new ClaimDataSummarizer();
		
		summarize.summerizeReport(reader.readReport());
		
		System.out.printf(summarize.returnReportString());
	}
	
	public static void pieChartCreation(){
		
		pieChartCreation pieChartCreation = new pieChartCreation();
	
		ExcelReader reader = new ExcelReader();
		ClaimDataSummarizer summarize = new ClaimDataSummarizer();
		summarize.summerizeReport(reader.readReport());
		
		String chartDir = pieChartCreation.faultPieChartFromClaimData(summarize);
		
		System.out.println(chartDir);
	}
	
	public static void chartToFacebookPostDemo(){
		
		pieChartCreation pieChartCreation = new pieChartCreation();
		
		ExcelReader reader = new ExcelReader();
		ClaimDataSummarizer summarize = new ClaimDataSummarizer();
		summarize.summerizeReport(reader.readReport());
		
		String chartDir = pieChartCreation.faultPieChartFromClaimData(summarize);
		
		FacebookPost fbp = new FacebookPost();
		
		fbp.createImagePostInGroup("Test Image Post", "517063855140936", chartDir);		
	}
	

}
