package com.post2facebook.controllers.view_controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.post2facebook.Gmail.EmailMessage;
import com.post2facebook.background.AutoPost;
import com.post2facebook.controllers.gmail_controller.GmailToFacebookController;

@Controller
@RequestMapping("/gmailtofacebook")
public class GmailToFacebookContoller {
	GmailToFacebookController g2f = new GmailToFacebookController();

	@RequestMapping("/")
	public String showMain(Model theModel){
		g2f.CheckForNewMessagesAddToDB();

		EmailMessage email = g2f.getNextUnpostedMessage();
		
		
		theModel.addAttribute("email", email);
		return "gmailtofacebook";
	}
	
	@RequestMapping("/Posted")
	public String postMessage(@ModelAttribute("email") EmailMessage theEmail){
		
		g2f.PostUnpostedMessageToFacebook();
		
		return "email-posted-confirmation";
	}
	
	@RequestMapping("/auto-post")
	public String autopost(Model theModel){
		theModel.addAttribute("timeunit", AutoPost.getTimeunit().toString());
		theModel.addAttribute("unit", AutoPost.getUnit());
		theModel.addAttribute("timeofnextpost", AutoPost.getTimeNextPost());
		
		
		return "auto-post";
	}
	
	
}
