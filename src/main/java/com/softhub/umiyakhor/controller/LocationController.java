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

import com.softhub.umiyakhor.entity.DistrictVo;
import com.softhub.umiyakhor.entity.VillageVo;
import com.softhub.umiyakhor.repository.DistrictRepo;
import com.softhub.umiyakhor.repository.VillageRepo;

@Controller
@RequestMapping("location")
public class LocationController {

	@Autowired
	DistrictRepo districtRepo;

	@Autowired
	VillageRepo villageRepo;

	private static String LOCATION_REDIRECT_PATH = "redirect:/location";

	@GetMapping("")
	public ModelAndView showLocationPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("location");
		modelAndView.addObject("districts", districtRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("villages", villageRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("districtVo", new DistrictVo());
		modelAndView.addObject("villageVo", new VillageVo());
		return modelAndView;
	}

	@PostMapping("district/add")
	public String addDistrict(@ModelAttribute DistrictVo districtVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		// add created log
		districtVo.setCreatedBy(principal.getName());
		districtVo.setCreatedDate(new Date());
		districtVo.setUpdatedBy(principal.getName());
		districtVo.setUpdatedDate(new Date());

		// save districtVo
		districtRepo.save(districtVo);

		// send message
		redirectAttributes.addFlashAttribute("message", "District inserted successfully.");

		return LOCATION_REDIRECT_PATH;
	}

	@GetMapping("district/update/{districtId}")
	public ModelAndView updateDistrict(@PathVariable long districtId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("location");
		modelAndView.addObject("districts", districtRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("villages", villageRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("villageVo", new VillageVo());
		Optional<DistrictVo> optional = districtRepo.findById(districtId);
		if (optional.isPresent()) {
			modelAndView.addObject("districtVo", optional.get());
		} else {
			modelAndView.addObject("districtVo", new DistrictVo());
		}
		return modelAndView;
	}

	@PostMapping("district/update")
	public String updateDistrict(@ModelAttribute DistrictVo districtVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		Optional<DistrictVo> optional = districtRepo.findById(districtVo.getId());
		if (optional.isPresent()) {

			// get object from optional
			DistrictVo dbDistrictVo = optional.get();

			// add updated log
			dbDistrictVo.setUpdatedBy(principal.getName());
			dbDistrictVo.setUpdatedDate(new Date());

			// set change field
			dbDistrictVo.setName(districtVo.getName());

			// update districtVo
			districtRepo.save(dbDistrictVo);

			// send message
			redirectAttributes.addFlashAttribute("message", "District update successfully.");
		} else {
			redirectAttributes.addFlashAttribute("error", "District not update !!!");
		}
		return LOCATION_REDIRECT_PATH;
	}

	@GetMapping("district/delete/{districtId}")
	public String deleteDistrict(@PathVariable long districtId, Principal principal,
			RedirectAttributes redirectAttributes) {

		Optional<DistrictVo> optional = districtRepo.findById(districtId);
		if (optional.isPresent()) {
			DistrictVo districtVo = optional.get();

			List<VillageVo> villageVos = villageRepo.findByDistrictVoId(districtVo.getId());
			if (villageVos != null && !villageVos.isEmpty()) {
				redirectAttributes.addFlashAttribute("error",
						"Please delete all village of this district before this !!!");
				return LOCATION_REDIRECT_PATH;
			}
			// add delete log
			districtVo.setDeletedBy(principal.getName());
			districtVo.setDeletedDate(new Date());

			// update before delete
			districtRepo.save(districtVo);

			// finally delete distroctVo
			districtRepo.deleteById(districtVo.getId());

			// send message
			redirectAttributes.addFlashAttribute("message", "District delete successfully.");
		} else {
			// send message
			redirectAttributes.addFlashAttribute("error", "District not deleted !!!");
		}
		return LOCATION_REDIRECT_PATH;
	}

	@PostMapping("district/check/unique/name")
	@ResponseBody
	public String checkDistrictNameIsUnique(@RequestParam String name, @RequestParam long districtId) {
		List<DistrictVo> districtVos = districtRepo.findByName(name.trim());
		if (districtVos != null && !districtVos.isEmpty()) {
			if (districtVos.size() == 1) {
				if (districtId != 0) {
					Optional<DistrictVo> optional = districtRepo.findById(districtId);
					if (optional.isPresent()) {
						DistrictVo districtVo = optional.get();
						if (districtVo.getName().trim().equalsIgnoreCase(name.trim())) {
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

	@PostMapping("village/add")
	public String addVillage(@ModelAttribute VillageVo villageVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		// add created log
		villageVo.setCreatedBy(principal.getName());
		villageVo.setCreatedDate(new Date());
		villageVo.setUpdatedBy(principal.getName());
		villageVo.setUpdatedDate(new Date());

		// save districtVo
		villageRepo.save(villageVo);

		// send message
		redirectAttributes.addFlashAttribute("message", "Village inserted successfully.");

		return LOCATION_REDIRECT_PATH;
	}

	@GetMapping("village/update/{villageId}")
	public ModelAndView updateVillage(@PathVariable long villageId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("location");
		modelAndView.addObject("districts", districtRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("villages", villageRepo.findAllByOrderByCreatedDateDesc());
		modelAndView.addObject("districtVo", new DistrictVo());

		Optional<VillageVo> optional = villageRepo.findById(villageId);
		if (optional.isPresent()) {
			modelAndView.addObject("villageVo", optional.get());
		} else {
			modelAndView.addObject("villageVo", new VillageVo());
		}
		return modelAndView;
	}

	@PostMapping("village/update")
	public String updateVillage(@ModelAttribute VillageVo villageVo, RedirectAttributes redirectAttributes,
			Principal principal) {

		Optional<VillageVo> optional = villageRepo.findById(villageVo.getId());
		if (optional.isPresent()) {

			// get object from optional
			VillageVo dbVillageVo = optional.get();

			// add updated log
			dbVillageVo.setUpdatedBy(principal.getName());
			dbVillageVo.setUpdatedDate(new Date());

			// set change field
			dbVillageVo.setName(villageVo.getName());
			dbVillageVo.setDistrictVo(villageVo.getDistrictVo());

			// update districtVo
			villageRepo.save(dbVillageVo);

			// send message
			redirectAttributes.addFlashAttribute("message", "Village update successfully.");
		} else {
			redirectAttributes.addFlashAttribute("error", "Village not update !!!");
		}
		return LOCATION_REDIRECT_PATH;
	}

	@GetMapping("village/delete/{villageId}")
	public String deleteVillage(@PathVariable long villageId, Principal principal,
			RedirectAttributes redirectAttributes) {

		Optional<VillageVo> optional = villageRepo.findById(villageId);
		if (optional.isPresent()) {
			VillageVo villageVo = optional.get();

			// add delete log
			villageVo.setDeletedBy(principal.getName());
			villageVo.setDeletedDate(new Date());

			// update before delete
			villageRepo.save(villageVo);

			// finally delete distroctVo
			villageRepo.deleteById(villageVo.getId());

			// send message
			redirectAttributes.addFlashAttribute("message", "Village delete successfully.");
		} else {
			// send message
			redirectAttributes.addFlashAttribute("error", "Village not deleted !!!");
		}

		return LOCATION_REDIRECT_PATH;
	}

	@PostMapping("village/check/unique/name")
	@ResponseBody
	public String checkVillageNameIsUnique(@RequestParam String name, @RequestParam long villageId) {
		List<VillageVo> villageVos = villageRepo.findByName(name.trim());
		if (villageVos != null && !villageVos.isEmpty()) {
			if (villageVos.size() == 1) {
				if (villageId != 0) {
					Optional<VillageVo> optional = villageRepo.findById(villageId);
					if (optional.isPresent()) {
						VillageVo villageVo = optional.get();
						if (villageVo.getName().trim().equalsIgnoreCase(name.trim())) {
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

	@GetMapping("getVillageByDistrictId/{districtId}")
	@ResponseBody
	public List<VillageVo> getVillagesByDistrictId(@PathVariable long districtId) {
		return villageRepo.findByDistrictVoId(districtId);
	}

}
