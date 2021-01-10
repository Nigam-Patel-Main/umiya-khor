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

import com.softhub.umiyakhor.entity.CustomerVo;
import com.softhub.umiyakhor.repository.CustomerRepo;
import com.softhub.umiyakhor.repository.DistrictRepo;
import com.softhub.umiyakhor.repository.VillageRepo;

@Controller
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	DistrictRepo districtRepo;

	@Autowired
	VillageRepo villageRepo;

	private static String CUSTOMER_REDIRECT_PATH = "redirect:/customer";

	@GetMapping("")
	public ModelAndView showCustomerPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("customer");
		modelAndView.addObject("customers", customerRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("districts", districtRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("villages", villageRepo.findAllByOrderByCreatedDateDesc());

		modelAndView.addObject("customerVo", new CustomerVo());
		return modelAndView;
	}

	@PostMapping("add")
	public String addCustomer(@ModelAttribute CustomerVo customerVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		// add created log
		customerVo.setCreatedBy(principal.getName());
		customerVo.setCreatedDate(new Date());
		customerVo.setUpdatedBy(principal.getName());
		customerVo.setUpdatedDate(new Date());

		// save customerVo
		customerRepo.save(customerVo);

		// send message
		redirectAttributes.addFlashAttribute("message", "Customer inserted successfully.");

		return CUSTOMER_REDIRECT_PATH;
	}

	@GetMapping("update/{customerId}")
	public ModelAndView updateCustomer(@PathVariable long customerId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("customer");
		modelAndView.addObject("customers", customerRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("districts", districtRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("villages", villageRepo.findAllByOrderByCreatedDateDesc());

		Optional<CustomerVo> optional = customerRepo.findById(customerId);
		if (optional.isPresent()) {
			modelAndView.addObject("customerVo", optional.get());
		} else {
			modelAndView.addObject("customerVo", new CustomerVo());
		}
		return modelAndView;
	}

	@PostMapping("update")
	public String updateCustomer(@ModelAttribute CustomerVo customerVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		Optional<CustomerVo> optional = customerRepo.findById(customerVo.getId());
		if (optional.isPresent()) {

			// get object from optional
			CustomerVo dbCustomerVo = optional.get();

			// add updated log
			dbCustomerVo.setUpdatedBy(principal.getName());
			dbCustomerVo.setUpdatedDate(new Date());

			// set change field
			dbCustomerVo.setName(customerVo.getName());
			dbCustomerVo.setDistrictVo(customerVo.getDistrictVo());
			dbCustomerVo.setVillageVo(customerVo.getVillageVo());
			dbCustomerVo.setAddress(customerVo.getAddress());
			dbCustomerVo.setMobileNumber(customerVo.getMobileNumber());

			// update customerVo
			customerRepo.save(dbCustomerVo);

			// send message
			redirectAttributes.addFlashAttribute("message", "Customer update successfully.");
		} else {
			redirectAttributes.addFlashAttribute("error", "Customer not update !!!");
		}
		return CUSTOMER_REDIRECT_PATH;
	}

	@GetMapping("delete/{customerId}")
	public String deleteCustomer(@PathVariable long customerId, Principal principal,
			RedirectAttributes redirectAttributes) {

		Optional<CustomerVo> optional = customerRepo.findById(customerId);
		if (optional.isPresent()) {
			CustomerVo customerVo = optional.get();

			// add delete log
			customerVo.setDeletedBy(principal.getName());
			customerVo.setDeletedDate(new Date());

			// update before delete
			customerRepo.save(customerVo);

			// finally delete distroctVo
			customerRepo.deleteById(customerVo.getId());

			// send message
			redirectAttributes.addFlashAttribute("message", "Customer delete successfully.");
		} else {
			// send message
			redirectAttributes.addFlashAttribute("error", "Customer not deleted !!!");
		}
		return CUSTOMER_REDIRECT_PATH;
	}

	@PostMapping("check/unique/name")
	@ResponseBody
	public String checkCustomerNameIsUnique(@RequestParam String name, @RequestParam long customerId) {

		List<CustomerVo> customerVos = customerRepo.findByName(name.trim());
		if (customerVos != null && !customerVos.isEmpty()) {
			if (customerVos.size() == 1) {
				if (customerId != 0) {
					Optional<CustomerVo> optional = customerRepo.findById(customerId);
					if (optional.isPresent()) {
						CustomerVo customerVo = optional.get();
						if (customerVo.getName().trim().equalsIgnoreCase(name.trim())) {
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
