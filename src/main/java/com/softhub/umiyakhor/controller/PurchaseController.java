package com.softhub.umiyakhor.controller;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softhub.umiyakhor.entity.PurchaseVo;
import com.softhub.umiyakhor.repository.ExpenseCategoryRepo;
import com.softhub.umiyakhor.repository.ProductRepo;
import com.softhub.umiyakhor.repository.PurchaseRepo;
import com.softhub.umiyakhor.repository.ShopRepo;

@Controller
@RequestMapping("purchase")
public class PurchaseController {

	@Autowired
	ShopRepo shopRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	PurchaseRepo purchaseRepo;

	@Autowired
	ExpenseCategoryRepo expenseCategoryRepo;

	@GetMapping("")
	public ModelAndView showPurchasePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase-new");
		modelAndView.addObject("products", productRepo.findAll());
		modelAndView.addObject("shops", shopRepo.findAll());
		modelAndView.addObject("expenseCategories", expenseCategoryRepo.findAll());
		modelAndView.addObject("purchasesOrders", purchaseRepo.findAllByOrderByCreatedDateDesc());
		return modelAndView;
	}

	@PostMapping("add")
	public String addPurchaseOrder(@ModelAttribute PurchaseVo purchaseVo, Principal principal,
			RedirectAttributes rediteAttributes) {

		// add log
		purchaseVo.setCreatedBy(principal.getName());
		purchaseVo.setCreatedDate(new Date());
		purchaseVo.setUpdatedBy(principal.getName());
		purchaseVo.setUpdatedDate(new Date());

		/* set purchaseVo refrence to purchasevoItem */
		if (purchaseVo.getPurchaseItemVos() != null) {
			purchaseVo.getPurchaseItemVos().stream()
					.forEach(purchaseItemVo -> purchaseItemVo.setPurchaseVo(purchaseVo));
		}

		/* set purchaseVo refrence to ExpenseItem */
		if (purchaseVo.getExpenseItemVos() != null) {
			purchaseVo.getExpenseItemVos().stream().forEach(expenseItemVo -> expenseItemVo.setPurchaseVo(purchaseVo));
		}

		/* save purchaseVo to database */
		purchaseRepo.save(purchaseVo);

		/* send message to user */
		rediteAttributes.addFlashAttribute("message", "Purchase Order inserted successfully.");

		return "redirect:/purchase";
	}

	@GetMapping("update/{purchaseId}")
	public ModelAndView showUpdatePage(@PathVariable long purchaseId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("purchase-update");
		modelAndView.addObject("products", productRepo.findAll());
		modelAndView.addObject("shops", shopRepo.findAll());
		modelAndView.addObject("expenseCategories", expenseCategoryRepo.findAll());
		Optional<PurchaseVo> optional = purchaseRepo.findById(purchaseId);
		if (optional.isPresent()) {
			modelAndView.addObject("purchaseVo", optional.get());
		} else {
			modelAndView.addObject("message", "Order number not exist");
		}
		return modelAndView;
	}

	@PostMapping("update")
	public String updatePurchaseOrder(@ModelAttribute PurchaseVo _purchaseVo, Principal principal,
			RedirectAttributes rediteAttributes) {

		Optional<PurchaseVo> optional = purchaseRepo.findById(_purchaseVo.getId());
		if (optional.isPresent()) {
			PurchaseVo purchaseVo = optional.get();

			/* update modified date */
			purchaseVo.setUpdatedBy(principal.getName());
			purchaseVo.setUpdatedDate(new Date());

			/* update shop and purchase date */
			purchaseVo.setShopVo(_purchaseVo.getShopVo());
			purchaseVo.setPurchaseDate(_purchaseVo.getPurchaseDate());

			/* set purchaseVo refrence to purchasevoItem */
			if (_purchaseVo.getPurchaseItemVos() != null) {
				_purchaseVo.getPurchaseItemVos().stream()
						.forEach(purchaseItemVo -> purchaseItemVo.setPurchaseVo(purchaseVo));
			}

			/* set purchaseVo refrence to ExpenseItem */
			if (_purchaseVo.getExpenseItemVos() != null) {
				_purchaseVo.getExpenseItemVos().stream()
						.forEach(expenseItemVo -> expenseItemVo.setPurchaseVo(purchaseVo));
			}

			/* delete purchase item from old purchaseVo */
			purchaseRepo.deletePurchaseItemByPurchaseId(_purchaseVo.getId());
			purchaseRepo.deleteExpenseItemByPurchaseId(_purchaseVo.getId());

			/* replace old purchase item to new purchase item */
			purchaseVo.setPurchaseItemVos(_purchaseVo.getPurchaseItemVos());

			/* replace old expense item to new expense item */
			purchaseVo.setExpenseItemVos(_purchaseVo.getExpenseItemVos());

			/* set total amount */
			purchaseVo.setProductAmount(_purchaseVo.getProductAmount());
			purchaseVo.setExpenseAmount(_purchaseVo.getExpenseAmount());
			purchaseVo.setTotalAmount(_purchaseVo.getTotalAmount());

			/* finnaly update purchase vo */
			purchaseRepo.save(purchaseVo);

			rediteAttributes.addFlashAttribute("message", "Purchase Order Updated Successfully.");
		} else {
			rediteAttributes.addFlashAttribute("error", "Purchase Order not updated.");
		}
		return "redirect:/purchase";
	}

	@ResponseBody
	@GetMapping("{purchaseId}")
	private PurchaseVo getPurchaseOrderById(@PathVariable long purchaseId) {
		Optional<PurchaseVo> optional = purchaseRepo.findById(purchaseId);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@GetMapping("delete/{purchaseId}")
	private String deletePurchaseOrderById(@PathVariable long purchaseId, RedirectAttributes rediteAttributes) {
		Optional<PurchaseVo> optional = purchaseRepo.findById(purchaseId);
		if (optional.isPresent()) {
			purchaseRepo.deleteById(purchaseId);
			rediteAttributes.addFlashAttribute("message", "Purchase order deleted successfully.");
		} else {
			rediteAttributes.addFlashAttribute("error", "Purchase order not updated.");
		}
		return "redirect:/purchase";
	}
}
