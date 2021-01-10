package com.softhub.umiyakhor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.softhub.umiyakhor.entity.PurchaseVo;
import com.softhub.umiyakhor.repository.ExpenseCategoryRepo;
import com.softhub.umiyakhor.repository.ProductRepo;
import com.softhub.umiyakhor.repository.ShopRepo;

@Controller
@RequestMapping("purchase")
public class PurchaseController {

	@Autowired
	ShopRepo shopRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	ExpenseCategoryRepo expenseCategoryRepo;

	@GetMapping("")
	public ModelAndView showPurchasePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase-new");
		modelAndView.addObject("products", productRepo.findAll());
		modelAndView.addObject("shops", shopRepo.findAll());
		modelAndView.addObject("expenseCategories", expenseCategoryRepo.findAll());
		return modelAndView;
	}

	@PostMapping("add")
	public String addPurchaseOrder(@ModelAttribute PurchaseVo purchaseVo) {
		System.out.println(purchaseVo);
		return "redirect:/purchase";
	}
}
