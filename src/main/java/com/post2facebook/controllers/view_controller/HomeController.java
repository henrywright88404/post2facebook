package com.post2facebook.controllers.view_controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String showPage(){
		return "main-menu";
	}
	@RequestMapping("/main-menu")
	public String showPage2(){
		return "main-menu";
	}
	
}
