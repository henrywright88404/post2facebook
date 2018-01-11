package com.post2facebook.postToFacebookApp;

import com.post2facebook.chartCreation.*;
import com.post2facebook.claim_data.ClaimDataSummarizer;
import com.post2facebook.claim_data.ExcelReader;
import com.post2facebook.facebook.FacebookPost;

public class MainApp {

	public static void main(String[] args) {
		GmailToFacebook gmailToFacebook = new GmailToFacebook();
		gmailToFacebook.CheckForNewMessagesAddToDB();
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
