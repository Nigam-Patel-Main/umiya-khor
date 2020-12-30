package com.softhub.umiyakhor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.softhub.umiyakhor.dto.OrderDTO;
import com.softhub.umiyakhor.dto.OrderItemDTO;

@Controller
@RequestMapping("purchase")
public class PurchaseController {
	
	@GetMapping("")
	public ModelAndView showPurchasePage() {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("purchase-new");
		return modelAndView;
	}
	
	@PostMapping("add")
	public String addPurchaseOrder() {
		return "redirect:/purchase";
	}
}
