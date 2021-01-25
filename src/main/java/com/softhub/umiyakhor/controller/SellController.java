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

import com.softhub.umiyakhor.entity.SellVo;
import com.softhub.umiyakhor.repository.CustomerRepo;
import com.softhub.umiyakhor.repository.ExpenseCategoryRepo;
import com.softhub.umiyakhor.repository.ProductRepo;
import com.softhub.umiyakhor.repository.SellRepo;

@Controller
@RequestMapping("sell")
public class SellController {

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	SellRepo sellRepo;

	@Autowired
	ExpenseCategoryRepo expenseCategoryRepo;

	@GetMapping("")
	public ModelAndView showSellPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("sell-new");
		modelAndView.addObject("products", productRepo.findAll());
		modelAndView.addObject("customers", customerRepo.findAll());
		modelAndView.addObject("expenseCategories", expenseCategoryRepo.findAll());
		modelAndView.addObject("sellsOrders", sellRepo.findAllByOrderByCreatedDateDesc());
		return modelAndView;
	}

	@PostMapping("add")
	public String addSellOrder(@ModelAttribute SellVo sellVo, Principal principal,
			RedirectAttributes rediteAttributes) {

		// add log
		sellVo.setCreatedBy(principal.getName());
		sellVo.setCreatedDate(new Date());
		sellVo.setUpdatedBy(principal.getName());
		sellVo.setUpdatedDate(new Date());

		/* set sellVo refrence to sellvoItem */
		if (sellVo.getSellItemVos() != null) {
			sellVo.getSellItemVos().stream().forEach(sellItemVo -> sellItemVo.setSellVo(sellVo));
		}

		/* set sellVo refrence to ExpenseItem */
		if (sellVo.getExpenseItemVos() != null) {
			sellVo.getExpenseItemVos().stream().forEach(expenseItemVo -> expenseItemVo.setSellVo(sellVo));
		}

		/* save sellVo to database */
		sellRepo.save(sellVo);

		/* send message to user */
		rediteAttributes.addFlashAttribute("message", "Sell inserted successfully.");

		return "redirect:/sell";
	}

	@GetMapping("update/{sellId}")
	public ModelAndView showUpdatePage(@PathVariable long sellId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("sell-update");
		modelAndView.addObject("products", productRepo.findAll());
		modelAndView.addObject("customers", customerRepo.findAll());
		modelAndView.addObject("expenseCategories", expenseCategoryRepo.findAll());
		Optional<SellVo> optional = sellRepo.findById(sellId);
		if (optional.isPresent()) {
			modelAndView.addObject("sellVo", optional.get());
		} else {
			modelAndView.addObject("message", "Sell number not exist");
		}
		return modelAndView;
	}

	@PostMapping("update")
	public String updateSellOrder(@ModelAttribute SellVo _sellVo, Principal principal,
			RedirectAttributes rediteAttributes) {

		Optional<SellVo> optional = sellRepo.findById(_sellVo.getId());
		if (optional.isPresent()) {
			SellVo sellVo = optional.get();

			/* update modified date */
			sellVo.setUpdatedBy(principal.getName());
			sellVo.setUpdatedDate(new Date());

			/* update shop and sell date */
			sellVo.setCustomerVo(_sellVo.getCustomerVo());
			sellVo.setSellDate(_sellVo.getSellDate());

			/* set sellVo refrence to sellvoItem */
			if (_sellVo.getSellItemVos() != null) {
				_sellVo.getSellItemVos().stream().forEach(sellItemVo -> sellItemVo.setSellVo(sellVo));
			}

			/* set sellVo refrence to ExpenseItem */
			if (_sellVo.getExpenseItemVos() != null) {
				_sellVo.getExpenseItemVos().stream().forEach(expenseItemVo -> expenseItemVo.setSellVo(sellVo));
			}

			/* delete sell item from old sellVo */
			sellRepo.deleteSellItemBySellId(_sellVo.getId());
			sellRepo.deleteExpenseItemBySellId(_sellVo.getId());

			/* replace old sell item to new sell item */
			sellVo.setSellItemVos(_sellVo.getSellItemVos());

			/* replace old expense item to new expense item */
			sellVo.setExpenseItemVos(_sellVo.getExpenseItemVos());

			/* set total amount */
			sellVo.setProductAmount(_sellVo.getProductAmount());
			sellVo.setExpenseAmount(_sellVo.getExpenseAmount());
			sellVo.setTotalAmount(_sellVo.getTotalAmount());

			/* finnaly update sell vo */
			sellRepo.save(sellVo);

			rediteAttributes.addFlashAttribute("message", "Sell Updated Successfully.");
		} else {
			rediteAttributes.addFlashAttribute("error", "Sell not updated.");
		}
		return "redirect:/sell";
	}

	@ResponseBody
	@GetMapping("{sellId}")
	public SellVo getSellOrderById(@PathVariable long sellId) {
		Optional<SellVo> optional = sellRepo.findById(sellId);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@GetMapping("delete/{sellId}")
	public String deleteSellOrderById(@PathVariable long sellId, RedirectAttributes rediteAttributes) {
		Optional<SellVo> optional = sellRepo.findById(sellId);
		if (optional.isPresent()) {
			sellRepo.deleteById(sellId);
			rediteAttributes.addFlashAttribute("message", "Sell  deleted successfully.");
		} else {
			rediteAttributes.addFlashAttribute("error", "Sell  not updated.");
		}
		return "redirect:/sell";
	}
}
