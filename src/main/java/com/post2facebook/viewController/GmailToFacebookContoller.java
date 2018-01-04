package com.post2facebook.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.post2facebook.Gmail.EmailMessage;
import com.post2facebook.postToFacebookApp.GmailToFacebook;

@Controller
@RequestMapping("/gmailtofacebook")
public class GmailToFacebookContoller {
	GmailToFacebook g2f = new GmailToFacebook();

	@RequestMapping("/")
	public String showMain(Model theModel){
		
		EmailMessage email = g2f.getNextUnpostedMessage();
		
		theModel.addAttribute("email", email);
		return "gmailtofacebook";
	}
	
	@RequestMapping("/Posted")
	public String postMessage(@ModelAttribute("email") EmailMessage theEmail){
		
		g2f.PostUnpostedMessageToFacebook();
		
		return "email-posted-confirmation";
	}
	
	
}
