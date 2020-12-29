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

import com.softhub.umiyakhor.entity.ShopVo;
import com.softhub.umiyakhor.repository.DistrictRepo;
import com.softhub.umiyakhor.repository.ShopRepo;
import com.softhub.umiyakhor.repository.VillageRepo;

@Controller
@RequestMapping("shop")
public class ShopController {

	@Autowired
	ShopRepo shopRepo;
	
	@Autowired
	DistrictRepo districtRepo;

	@Autowired
	VillageRepo villageRepo;

	private static String SHOP_REDIRECT_PATH = "redirect:/shop";

	@GetMapping("")
	public ModelAndView showShopPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("shop");
		modelAndView.addObject("shops", shopRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("districts", districtRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("villages", villageRepo.findAllByOrderByCreatedDateDesc());
		
		modelAndView.addObject("shopVo", new ShopVo());
		return modelAndView;
	}

	@PostMapping("add")
	public String addShop(@ModelAttribute ShopVo shopVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		// add created log
		shopVo.setCreatedBy(principal.getName());
		shopVo.setCreatedDate(new Date());
		shopVo.setUpdatedBy(principal.getName());
		shopVo.setUpdatedDate(new Date());

		// save shopVo
		shopRepo.save(shopVo);

		// send message
		redirectAttributes.addFlashAttribute("message", "Shop inserted successfully.");

		return SHOP_REDIRECT_PATH;
	}

	@GetMapping("update/{shopId}")
	public ModelAndView updateShop(@PathVariable long shopId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("shop");
		modelAndView.addObject("shops", shopRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("districts", districtRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("villages", villageRepo.findAllByOrderByCreatedDateDesc());
		
		Optional<ShopVo> optional = shopRepo.findById(shopId);
		if (optional.isPresent()) {
			modelAndView.addObject("shopVo", optional.get());
		} else {
			modelAndView.addObject("shopVo", new ShopVo());
		}
		return modelAndView;
	}
	
	@PostMapping("update")
	public String updateShop(@ModelAttribute ShopVo shopVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		Optional<ShopVo> optional = shopRepo.findById(shopVo.getId());
		if (optional.isPresent()) {

			// get object from optional
			ShopVo dbShopVo = optional.get();

			// add updated log
			dbShopVo.setUpdatedBy(principal.getName());
			dbShopVo.setUpdatedDate(new Date());

			// set change field
			dbShopVo.setName(shopVo.getName());
			dbShopVo.setDistrictVo(shopVo.getDistrictVo());
			dbShopVo.setVillageVo(shopVo.getVillageVo());
			dbShopVo.setAddress(shopVo.getAddress());
			dbShopVo.setMobileNumber(shopVo.getMobileNumber());

			// update shopVo
			shopRepo.save(dbShopVo);

			// send message
			redirectAttributes.addFlashAttribute("message", "Shop update successfully.");
		} else {
			redirectAttributes.addFlashAttribute("error", "Shop not update !!!");
		}
		return SHOP_REDIRECT_PATH;
	}
	
	@GetMapping("delete/{shopId}")
	public String deleteShop(@PathVariable long shopId, Principal principal,
			RedirectAttributes redirectAttributes) {

		Optional<ShopVo> optional = shopRepo.findById(shopId);
		if (optional.isPresent()) {
			ShopVo shopVo = optional.get();

			// add delete log
			shopVo.setDeletedBy(principal.getName());
			shopVo.setDeletedDate(new Date());

			// update before delete
			shopRepo.save(shopVo);

			// finally delete distroctVo
			shopRepo.deleteById(shopVo.getId());

			// send message
			redirectAttributes.addFlashAttribute("message", "Shop delete successfully.");
		} else {
			// send message
			redirectAttributes.addFlashAttribute("error", "Shop not deleted !!!");
		}
		return SHOP_REDIRECT_PATH;
	}
	
	@PostMapping("check/unique/name")
	@ResponseBody
	public Map<String, Boolean> checkShopNameIsUnique(@RequestParam String name, @RequestParam long shopId) {
		HashMap<String, Boolean> map = new HashMap<>();
		List<ShopVo> shopVos = shopRepo.findByName(name.trim());
		if (shopVos != null && !shopVos.isEmpty()) {
			if (shopVos.size() == 1) {
				if (shopId != 0) {
					Optional<ShopVo> optional = shopRepo.findById(shopId);
					if (optional.isPresent()) {
						ShopVo shopVo = optional.get();
						if (shopVo.getName().trim().equalsIgnoreCase(name.trim())) {
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
