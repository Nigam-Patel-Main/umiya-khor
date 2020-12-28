package com.softhub.umiyakhor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("customer")
public class CustomerController {
	
	@GetMapping("")
	public ModelAndView showCustomerPage(ModelAndView modelAndView) {
		modelAndView.setViewName("customer");
		return modelAndView;
	}
}
