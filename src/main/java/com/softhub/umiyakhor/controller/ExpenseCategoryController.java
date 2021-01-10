package com.softhub.umiyakhor.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softhub.umiyakhor.entity.ExpenseCategoryVo;
import com.softhub.umiyakhor.repository.ExpenseCategoryRepo;

@Controller
@RequestMapping("expenseCategory")
public class ExpenseCategoryController {

	@Autowired
	ExpenseCategoryRepo expenseCategoryRepo;

	private static String EXPENSE_REDIRECT_PATH = "redirect:/expenseCategory";

	@GetMapping("")
	public ModelAndView showExpenseCategoryPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("expense-category");
		modelAndView.addObject("expenseCategorys", expenseCategoryRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("expenseCategoryVo", new ExpenseCategoryVo());
		return modelAndView;
	}

	@PostMapping("add")
	public String addExpenseCategory(@ModelAttribute ExpenseCategoryVo expenseCategoryVo,
			RedirectAttributes redirectAttributes, Principal principal) {

		// add created log
		expenseCategoryVo.setCreatedBy(principal.getName());
		expenseCategoryVo.setCreatedDate(new Date());
		expenseCategoryVo.setUpdatedBy(principal.getName());
		expenseCategoryVo.setUpdatedDate(new Date());

		// save expenseCategoryVo
		expenseCategoryRepo.save(expenseCategoryVo);

		// send message
		redirectAttributes.addFlashAttribute("message", "Expense Category inserted successfully.");

		return EXPENSE_REDIRECT_PATH;
	}

	@GetMapping("update/{expenseCategoryId}")
	public ModelAndView updateExpenseCategory(@PathVariable long expenseCategoryId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("expense-category");
		modelAndView.addObject("expenseCategorys", expenseCategoryRepo.findAllByOrderByCreatedDateDesc());
		Optional<ExpenseCategoryVo> optional = expenseCategoryRepo.findById(expenseCategoryId);
		if (optional.isPresent()) {
			modelAndView.addObject("expenseCategoryVo", optional.get());
		} else {
			modelAndView.addObject("expenseCategoryVo", new ExpenseCategoryVo());
		}
		return modelAndView;
	}

	@PostMapping("update")
	public String updateExpenseCategory(@ModelAttribute ExpenseCategoryVo expenseCategoryVo,
			RedirectAttributes redirectAttributes, Principal principal) {

		Optional<ExpenseCategoryVo> optional = expenseCategoryRepo.findById(expenseCategoryVo.getId());
		if (optional.isPresent()) {

			// get object from optional
			ExpenseCategoryVo dbExpenseCategoryVo = optional.get();

			// add updated log
			dbExpenseCategoryVo.setUpdatedBy(principal.getName());
			dbExpenseCategoryVo.setUpdatedDate(new Date());

			// set change field
			dbExpenseCategoryVo.setName(expenseCategoryVo.getName());

			// update expenseCategoryVo
			expenseCategoryRepo.save(dbExpenseCategoryVo);

			// send message
			redirectAttributes.addFlashAttribute("message", "Expense Category update successfully.");
		} else {
			redirectAttributes.addFlashAttribute("error", "Expense Category not update !!!");
		}
		return EXPENSE_REDIRECT_PATH;
	}

	@GetMapping("delete/{expenseCategoryId}")
	public String deleteExpenseCategory(@PathVariable long expenseCategoryId, Principal principal,
			RedirectAttributes redirectAttributes) {

		Optional<ExpenseCategoryVo> optional = expenseCategoryRepo.findById(expenseCategoryId);
		if (optional.isPresent()) {
			ExpenseCategoryVo expenseCategoryVo = optional.get();

			// add delete log
			expenseCategoryVo.setDeletedBy(principal.getName());
			expenseCategoryVo.setDeletedDate(new Date());

			// update before delete
			expenseCategoryRepo.save(expenseCategoryVo);

			// finally delete distroctVo
			expenseCategoryRepo.deleteById(expenseCategoryVo.getId());

			// send message
			redirectAttributes.addFlashAttribute("message", "Expense Category delete successfully.");
		} else {
			// send message
			redirectAttributes.addFlashAttribute("error", "Expense Category not deleted !!!");
		}
		return EXPENSE_REDIRECT_PATH;
	}

	@PostMapping("check/unique/name")
	@ResponseBody
	public String checkExpenseCategoryNameIsUnique(@RequestParam String name, @RequestParam long expenseCategoryId) {

		List<ExpenseCategoryVo> expenseCategoryVos = expenseCategoryRepo.findByName(name.trim());
		if (expenseCategoryVos != null && !expenseCategoryVos.isEmpty()) {
			if (expenseCategoryVos.size() == 1) {
				if (expenseCategoryId != 0) {
					Optional<ExpenseCategoryVo> optional = expenseCategoryRepo.findById(expenseCategoryId);
					if (optional.isPresent()) {
						ExpenseCategoryVo expenseCategoryVo = optional.get();
						if (expenseCategoryVo.getName().trim().equalsIgnoreCase(name.trim())) {
							return "true";
						}
					}
				}
			}
			return "false";
		} else {
			return "true";
		}
	}
}
