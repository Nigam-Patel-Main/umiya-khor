package com.softhub.umiyakhor.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.softhub.umiyakhor.entity.ExpenseVo;
import com.softhub.umiyakhor.repository.DistrictRepo;
import com.softhub.umiyakhor.repository.ExpenseRepo;
import com.softhub.umiyakhor.repository.VillageRepo;

@Controller
@RequestMapping("expense")
public class ExpenseController {

	@Autowired
	ExpenseRepo expenseRepo;
	


	private static String EXPENSE_REDIRECT_PATH = "redirect:/expense";

	@GetMapping("")
	public ModelAndView showExpensePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("expense");
		modelAndView.addObject("expenses", expenseRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("expenseVo", new ExpenseVo());
		return modelAndView;
	}

	@PostMapping("add")
	public String addExpense(@ModelAttribute ExpenseVo expenseVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		// add created log
		expenseVo.setCreatedBy(principal.getName());
		expenseVo.setCreatedDate(new Date());
		expenseVo.setUpdatedBy(principal.getName());
		expenseVo.setUpdatedDate(new Date());

		// save expenseVo
		expenseRepo.save(expenseVo);

		// send message
		redirectAttributes.addFlashAttribute("message", "Expense inserted successfully.");

		return EXPENSE_REDIRECT_PATH;
	}

	@GetMapping("update/{expenseId}")
	public ModelAndView updateExpense(@PathVariable long expenseId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("expense");
		modelAndView.addObject("expenses", expenseRepo.findAllByOrderByCreatedDateDesc());
		Optional<ExpenseVo> optional = expenseRepo.findById(expenseId);
		if (optional.isPresent()) {
			modelAndView.addObject("expenseVo", optional.get());
		} else {
			modelAndView.addObject("expenseVo", new ExpenseVo());
		}
		return modelAndView;
	}
	
	@PostMapping("update")
	public String updateExpense(@ModelAttribute ExpenseVo expenseVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		Optional<ExpenseVo> optional = expenseRepo.findById(expenseVo.getId());
		if (optional.isPresent()) {

			// get object from optional
			ExpenseVo dbExpenseVo = optional.get();

			// add updated log
			dbExpenseVo.setUpdatedBy(principal.getName());
			dbExpenseVo.setUpdatedDate(new Date());

			// set change field
			dbExpenseVo.setName(expenseVo.getName());

			// update expenseVo
			expenseRepo.save(dbExpenseVo);

			// send message
			redirectAttributes.addFlashAttribute("message", "Expense update successfully.");
		} else {
			redirectAttributes.addFlashAttribute("error", "Expense not update !!!");
		}
		return EXPENSE_REDIRECT_PATH;
	}
	
	@GetMapping("delete/{expenseId}")
	public String deleteExpense(@PathVariable long expenseId, Principal principal,
			RedirectAttributes redirectAttributes) {

		Optional<ExpenseVo> optional = expenseRepo.findById(expenseId);
		if (optional.isPresent()) {
			ExpenseVo expenseVo = optional.get();

			// add delete log
			expenseVo.setDeletedBy(principal.getName());
			expenseVo.setDeletedDate(new Date());

			// update before delete
			expenseRepo.save(expenseVo);

			// finally delete distroctVo
			expenseRepo.deleteById(expenseVo.getId());

			// send message
			redirectAttributes.addFlashAttribute("message", "Expense delete successfully.");
		} else {
			// send message
			redirectAttributes.addFlashAttribute("error", "Expense not deleted !!!");
		}
		return EXPENSE_REDIRECT_PATH;
	}
	
	@PostMapping("check/unique/name")
	@ResponseBody
	public Map<String, Boolean> checkExpenseNameIsUnique(@RequestParam String name, @RequestParam long expenseId) {
		HashMap<String, Boolean> map = new HashMap<>();
		List<ExpenseVo> expenseVos = expenseRepo.findByName(name.trim());
		if (expenseVos != null && !expenseVos.isEmpty()) {
			if (expenseVos.size() == 1) {
				if (expenseId != 0) {
					Optional<ExpenseVo> optional = expenseRepo.findById(expenseId);
					if (optional.isPresent()) {
						ExpenseVo expenseVo = optional.get();
						if (expenseVo.getName().trim().equalsIgnoreCase(name.trim())) {
							map.put("valid", true);
							return map;
						}
					}
				}
			}
			map.put("valid", false);
			return map;
		} else {
			map.put("valid", true);
			return map;
		}
	}
}
