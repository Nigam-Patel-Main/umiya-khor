package com.softhub.umiyakhor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping(value = {"","/"})
	public String redirectToNewOrder() {
		return "navbar";
	}
	
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
}
