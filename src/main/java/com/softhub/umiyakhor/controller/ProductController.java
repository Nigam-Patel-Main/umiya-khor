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

import com.softhub.umiyakhor.entity.ProductVo;
import com.softhub.umiyakhor.repository.ProductRepo;

@Controller
@RequestMapping("product")
public class ProductController {

	@Autowired
	ProductRepo productRepo;

	private static String PRODUCT_REDIRECT_PATH = "redirect:/product";

	@GetMapping("")
	public ModelAndView showProductPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("product");
		modelAndView.addObject("products", productRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("productVo", new ProductVo());
		return modelAndView;
	}

	@PostMapping("add")
	public String addProduct(@ModelAttribute ProductVo productVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		// add created log
		productVo.setCreatedBy(principal.getName());
		productVo.setCreatedDate(new Date());
		productVo.setUpdatedBy(principal.getName());
		productVo.setUpdatedDate(new Date());

		// save productVo
		productRepo.save(productVo);

		// send message
		redirectAttributes.addFlashAttribute("message", "Product inserted successfully.");

		return PRODUCT_REDIRECT_PATH;
	}

	@GetMapping("update/{productId}")
	public ModelAndView updateProduct(@PathVariable long productId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("product");
		modelAndView.addObject("products", productRepo.findAllByOrderByCreatedDateDesc());
		Optional<ProductVo> optional = productRepo.findById(productId);
		if (optional.isPresent()) {
			modelAndView.addObject("productVo", optional.get());
		} else {
			modelAndView.addObject("productVo", new ProductVo());
		}
		return modelAndView;
	}

	@PostMapping("update")
	public String updateProduct(@ModelAttribute ProductVo productVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		Optional<ProductVo> optional = productRepo.findById(productVo.getId());
		if (optional.isPresent()) {

			// get object from optional
			ProductVo dbProductVo = optional.get();

			// add updated log
			dbProductVo.setUpdatedBy(principal.getName());
			dbProductVo.setUpdatedDate(new Date());

			// set change field
			dbProductVo.setName(productVo.getName());
			dbProductVo.setPrice(productVo.getPrice());

			// update productVo
			productRepo.save(dbProductVo);

			// send message
			redirectAttributes.addFlashAttribute("message", "Product update successfully.");
		} else {
			redirectAttributes.addFlashAttribute("error", "Product not update !!!");
		}
		return PRODUCT_REDIRECT_PATH;
	}

	@GetMapping("delete/{productId}")
	public String deleteProduct(@PathVariable long productId, Principal principal,
			RedirectAttributes redirectAttributes) {

		Optional<ProductVo> optional = productRepo.findById(productId);
		if (optional.isPresent()) {
			ProductVo productVo = optional.get();

			// add delete log
			productVo.setDeletedBy(principal.getName());
			productVo.setDeletedDate(new Date());

			// update before delete
			productRepo.save(productVo);

			// finally delete distroctVo
			productRepo.deleteById(productVo.getId());

			// send message
			redirectAttributes.addFlashAttribute("message", "Product delete successfully.");
		} else {
			// send message
			redirectAttributes.addFlashAttribute("error", "Product not deleted !!!");
		}
		return PRODUCT_REDIRECT_PATH;
	}

	@PostMapping("check/unique/name")
	@ResponseBody
	public String checkProductNameIsUnique(@RequestParam String name, @RequestParam long productId) {

		List<ProductVo> productVos = productRepo.findByName(name.trim());
		if (productVos != null && !productVos.isEmpty()) {
			if (productVos.size() == 1) {
				if (productId != 0) {
					Optional<ProductVo> optional = productRepo.findById(productId);
					if (optional.isPresent()) {
						ProductVo productVo = optional.get();
						if (productVo.getName().trim().equalsIgnoreCase(name.trim())) {
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
