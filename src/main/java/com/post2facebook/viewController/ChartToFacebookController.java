package com.post2facebook.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/charttofacebook")
public class ChartToFacebookController {

	@RequestMapping("/")
	public String showMain(){
		return"postreport";
	}
	
	
}
